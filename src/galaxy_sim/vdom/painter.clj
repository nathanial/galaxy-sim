(ns galaxy-sim.vdom.painter
  (:import [javax.swing
            SwingUtilities JFrame JPanel JLabel
            JLayeredPane]
           [java.awt Color BorderLayout Dimension RenderingHints Rectangle]
           [java.awt.geom Ellipse2D$Double]))

(defn- to-awt-color [{:keys [red,green,blue,alpha]}]
  (Color. red green blue alpha))

(defmulti paint-element (fn [graphics element] (:type element)))

(defmethod paint-element :ellipse [graphics element]
  (let [color (:color element)
        [a b c d] (:vertices element)
        shape (Ellipse2D$Double. a b c d)]
    (doto graphics
      (.setColor (to-awt-color color)))
    (when (:fill element)
      (.fill graphics shape))))
