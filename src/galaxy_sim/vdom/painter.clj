(ns galaxy-sim.vdom.painter
  (:use [com.rpl.specter])
  (:require [swing.core])
  (:import (java.awt Graphics2D Color)
           (java.awt.image BufferedImage)
           (java.awt.geom AffineTransform Ellipse2D$Double)))


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

(defn create-transform [scale t1 t2]
  (doto (AffineTransform.)
    (.translate (:x t1) (:y t1))
    (.scale (:x scale) (:y scale))
    (.translate (:x t2) (:y t2))
    ))

; paint with scale only, not translation
; fixed size tile, elements centered within
