(ns galaxy-sim.vdom
  (:import [javax.swing
            SwingUtilities JFrame JPanel JLabel
            JLayeredPane]
           [java.awt Color BorderLayout Dimension RenderingHints Rectangle]
           [java.awt.geom Ellipse2D$Double]))

(defmacro invoke-later [& args]
  `(SwingUtilities/invokeLater (fn [] ~@args)))

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

(defn- create-dom-view [dom]
  (proxy [JPanel] []
    (paintComponent [g]
      (proxy-super paintComponent g)
      (doto g
        (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
        (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)
        (.setColor (Color. 0 0 0))
        (.fillRect 0 0 (.. this (getSize) width) (.. this (getSize) height)))
      (doseq [element dom]
        (println "PAINT" element)
        (paint-element g element)))

    (getPreferredSize  []
      (Dimension. 1000 1000))))

(defn- create-simulation-view [dom]
  (let [panel (JPanel. )
        pane (JLayeredPane.)
        domView (create-dom-view dom)]
    (doto domView
      (.setBounds (Rectangle. 0 0 1000 1000)))
    (doto pane
      (.add domView 51)
      (.setPreferredSize (Dimension. 1000 1000)))
    (doto panel
      (.setLayout (BorderLayout. ))
      (.setBackground (Color/white))
      (.add pane)
      )))

(defn render [dom]
  (invoke-later
    (let [frame (JFrame. "Galactic Simulation")]
      (doto frame
        (.setResizable true)
        (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
        (.setBackground Color/white)
        (.add (create-simulation-view dom))
        (.pack)
        (.show)))))
