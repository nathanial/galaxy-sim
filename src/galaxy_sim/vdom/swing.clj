(ns galaxy-sim.vdom.swing
  (:require [galaxy-sim.vdom.painter :as painter]
            [galaxy-sim.globals :as globals])
  (:import [javax.swing
            SwingUtilities JFrame JPanel JLabel
            JLayeredPane]
           [java.awt Color BorderLayout Dimension RenderingHints Rectangle]
           [java.awt.geom Ellipse2D$Double AffineTransform]))

(defmacro invoke-later [& args]
  `(SwingUtilities/invokeLater (fn [] ~@args)))

(defn basic-transform []
  (let [transform (AffineTransform. )]
    (doto transform
      (.translate 0.0 0.0)
      (.scale 0.25 0.25))))

(defn- create-dom-view [dom]
  (proxy [JPanel] []
    (paintComponent [g]
      (proxy-super paintComponent g)
      (doto g
        (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
        (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)
        (.setColor (Color. 0 0 0))
        (.setTransform (AffineTransform.))
        (.fillRect 0 0 (.. this (getSize) width) (.. this (getSize) height))
        (.setTransform (basic-transform)))
      (doseq [element dom]
        (painter/paint-element g element)))
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
