(ns galaxy-sim.vdom.painter
  (:import [javax.swing
            SwingUtilities JFrame JPanel JLabel
            JLayeredPane]
           [java.awt Color BorderLayout Dimension RenderingHints Rectangle]
           [java.awt.geom Ellipse2D$Double]))

(defmulti paint-element (fn [graphics element] (:type element)))

(defmethod paint-element :ellipse [graphics element]
  (let [[red green blue] (:color element)
        [a b c d] (:vertices element)
        shape (Ellipse2D$Double. a b c d)]
    (doto graphics
      (.setColor (Color. red green blue)))
    (when (:fill element)
      (.fill graphics shape))))
