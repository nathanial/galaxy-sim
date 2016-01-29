(ns galaxy-sim.vdom.painter
  (:import [java.awt Color Graphics2D RenderingHints]
           [java.awt.geom Ellipse2D$Double]
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

(defn paint-all [^Graphics2D g transform elements]
  (let [scale (:scale transform)
        tiles (tiles/render-as-tiles scale elements paint-element)]
    (doseq [tile tiles]
      (let [^BufferedImage image (:image tile)]
        (doto g
          (.setTransform (swing.core/add-translations (:translate transform) (:translate tile)))
          (.drawImage image 0 0 nil))))))