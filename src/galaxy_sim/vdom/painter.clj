(ns galaxy-sim.vdom.painter
  (:import [java.awt Color Graphics2D RenderingHints]
           [java.awt.geom Ellipse2D$Double AffineTransform]
           (java.awt.image BufferedImage))
  (:require [galaxy-sim.vdom.tiles :as tiles]
            [swing.core])
  (:use [com.rpl.specter]))

(defmulti paint-element (fn [_ element] (:type element)))

(defmethod paint-element :circle [^Graphics2D graphics element]
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

(defn paint-all [^Graphics2D g transform elements]
  (let [scale (:scale transform)
        tiles (tiles/render-as-tiles {:x 1 :y 1} elements paint-element)]
    (doseq [tile tiles]
      (let [^BufferedImage image (:image tile)
            transform (create-transform scale (:translate transform) (:translate tile))]
        (doto g
          (.setTransform transform)
          (.drawImage image 0 0 nil))))))