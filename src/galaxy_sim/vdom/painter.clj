(ns galaxy-sim.vdom.painter
  (:use [com.rpl.specter])
  (:require [swing.core])
  (:import (java.awt RenderingHints Graphics2D Color Rectangle)
           (java.awt.image BufferedImage)
           (java.awt.geom AffineTransform Ellipse2D$Double)))

(defn- abs [n] (max n (- n)))

(def tile-width 100)
(def tile-height 100)

(defmulti ^:private paint-element (fn [_ element] (:type element)))

(defmethod ^:private paint-element :circle [^Graphics2D graphics element]
  (let [[^Integer red ^Integer green ^Integer blue alpha?] (:color element)
        ^Integer alpha (if (nil? alpha?) 255 alpha?)
        x (:x element)
        y (:y element)
        shape (Ellipse2D$Double. x y (* 2 (:radius element)) (* 2 (:radius element)))]
    (doto graphics
      (.setColor (Color. red green blue alpha))
      (.draw shape))
    (when (:fill element)
      (.fill graphics shape))))

(defn- create-transform [scale t1 t2]
  (doto (AffineTransform.)
    (.translate (:x t1) (:y t1))
    (.scale (:x scale) (:y scale))
    (.translate (:x t2) (:y t2))
    ))

(def ^:private scales (map #(/ %1 10) (range 1 51)))

(defn- nearest-scale [x]
  (let [distances (map (fn [s] {:scale s, :distance (abs (- x s))}) scales)
        closest (apply min-key :distance distances)]
    (:scale closest)))

(defn- incremental-scale [{:keys [x y]}]
  (let [result {:x (nearest-scale x) :y (nearest-scale y)}]
    (println "Incremental Scale" x y result)
    result))

; paint with scale only, not translation
; fixed size tile, elements centered within

(defn- double-buffer [width height transform paint]
  (let [buffer (swing.core/create-compatible-image width height)
        graphics (.createGraphics buffer)]
    (doto graphics
      (swing.core/set-graphic-defaults)
      (.setTransform (swing.core/to-affine transform))
      (paint)
      (.dispose))
    buffer))

(defn- determine-bounds [elements]
  (let [x-coords (select [ALL :x] elements)
        y-coords (select [ALL :y] elements)
        min-x (apply min x-coords)
        max-x (apply max x-coords)
        min-y (apply min y-coords)
        max-y (apply max y-coords)]
    {
     :x min-x,
     :y min-y,
     :width (abs (- min-x max-x)),
     :height (abs (- min-y max-y))
     }))

(defn- in-range [element {:keys [x y width height]}]
  (and
    (>= (:x element) x)
    (<= (:x element) (+ x width))
    (>= (:y element) y)
    (<= (:y element) (+ y height))))


(defn- split-into-tiles [elements]
  (let [bounds (determine-bounds elements)
        x-parts (/ (:width bounds) tile-width)
        y-parts (/ (:height bounds) tile-height)
        min-x (:x bounds)
        min-y (:y bounds)]
    (for [x (range 0 x-parts)
          y (range 0 y-parts)]
      (let [tile-bounds {:x      (+ min-x (* x tile-width))
                         :y      (+ min-y (* y tile-height))
                         :width  tile-width
                         :height tile-height}
            tile-elements (->> elements
                               (filter #(in-range %1 tile-bounds))
                               (map #(assoc %1 :x (- (:x %1) (:x tile-bounds))
                                              :y (- (:y %1) (:y tile-bounds)))))]
        {:translate {:x (+ min-x (* tile-width x)) :y (+ min-y (* tile-height y))}
         :elements  tile-elements}))))

(def ^:private render-tile-image
  (memoize
    (fn [elements]
      (println "RENDER THE BEAST")
      (let [image (swing.core/create-compatible-image tile-width tile-height)
            graphics (.createGraphics image)]
        (swing.core/set-graphic-defaults graphics)
        (doseq [element elements]
          (paint-element graphics element))
        (.dispose graphics)
        image))))

(defn- is-visible? [{:keys [x y width height]} tile]
  (let [vrect (Rectangle. x y width height)
        trans (:translate tile)
        trect (Rectangle. (:x trans) (:y trans) tile-width tile-height)]
    (.intersects vrect trect)))

;result in galactic coordinates
(defn- determine-viewport [transform window]
  (let [translate (:translate transform)
        scale (:scale transform)]
    {:x      (/ (- (:x translate)) (:x scale))
     :y      (/ (- (:y translate)) (:y scale))
     :width  (/ (:width window) (:x scale))
     :height (/ (:height window) (:y scale))
     }))

(defn- render-as-tiles [transform window elements]
  (let [viewport (determine-viewport transform window)]
    (println "Viewport" viewport)
    (for [tile (split-into-tiles elements)]
      (let [image (render-tile-image (:elements tile))]
        (assoc tile :image image
                    :visible (is-visible? viewport tile))))))

(defn paint-all [^Graphics2D g transform window elements]
  (let [scale (:scale transform)
        tiles (render-as-tiles transform window elements)
        visible-tiles (filter :visible tiles)]
    (println "Visible" (count visible-tiles) "/" (count tiles))
    (doseq [tile visible-tiles]
      (let [^BufferedImage image (:image tile)
            transform (create-transform scale (:translate transform) (:translate tile))]
        (doto g
          (.setTransform transform)
          (.drawImage image 0 0 nil)
          (.setColor Color/red)
          (.drawRect 0 0 (dec tile-width) (dec tile-height)))))))