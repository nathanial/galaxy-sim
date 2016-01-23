(ns galaxy-sim.vdom.painter
  (:import [java.awt Color Graphics2D]
           [java.awt.geom Ellipse2D$Double]))

(defmulti paint-element (fn [_ element] (:type element)))

(defmethod paint-element :ellipse [^Graphics2D graphics element]
  (let [[^Integer red ^Integer green ^Integer blue] (:color element)
        [a b c d] (:vertices element)
        shape (Ellipse2D$Double. a b c d)]
    (doto graphics
      (.setColor (Color. red green blue)))
    (when (:fill element)
      (.fill graphics shape))))
