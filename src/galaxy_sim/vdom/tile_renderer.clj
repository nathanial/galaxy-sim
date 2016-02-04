(ns galaxy-sim.vdom.tile-renderer
  (:use [com.rpl.specter])
  (:require [galaxy-sim.vdom.painter :as painter])
  (:import (java.awt Rectangle)))

(defn- abs [n] (max n (- n)))

(def tile-width 1000)
(def tile-height 1000)

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


(def ^:private split-into-tiles
  (memoize
    (fn [elements]
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
             :elements  tile-elements}))))))

(def ^:private render-tile-image
  (memoize
    (fn [elements]
      (println "RENDER THE BEAST")
      (let [image (swing.core/create-compatible-image tile-width tile-height)
            graphics (.createGraphics image)]
        (swing.core/set-graphic-defaults graphics)
        (doseq [element elements]
          (painter/paint-element graphics element))
        (.dispose graphics)
        image))))

(defn- is-visible? [{:keys [x y width height]} tile]
  (let [vrect (Rectangle. x y width height)
        trans (:translate tile)
        trect (Rectangle. (:x trans) (:y trans) tile-width tile-height)]
    (.intersects vrect trect)))

(defn- determine-viewport [transform window]
  (let [translate (:translate transform)
        scale (:scale transform)]
    {:x      (/ (- (:x translate)) (:x scale))
     :y      (/ (- (:y translate)) (:y scale))
     :width  (/ (:width window) (:x scale))
     :height (/ (:height window) (:y scale))
     }))

;make this method asynchronous?
;maybe return a channel of tiles
;maybe a draw chan that takes {:transform, :image} arguments
(defn render-as-tiles [transform window elements]
  (let [viewport (determine-viewport transform window)]
    (for [tile (->> (split-into-tiles elements) (filter #(is-visible? viewport %1)))]
      (let [image (render-tile-image (:elements tile))]
        (assoc tile :image image)))))