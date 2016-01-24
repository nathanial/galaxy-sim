(ns galaxy-sim.vdom.swing
  (:require [galaxy-sim.vdom.painter :as painter]
            [galaxy-sim.globals :as globals]
            [swing.core])
  (:import [javax.swing JFrame JPanel JLayeredPane]
           [java.awt Color BorderLayout Dimension RenderingHints Rectangle Graphics2D]
           [java.awt.geom AffineTransform]))

(defn basic-transform [sim]
  (let [transform (AffineTransform. )
        {:keys [scale translate]} (:transform sim)]
    (doto transform
      (.translate (:x translate) (:y translate))
      (.scale (:x scale) (:y scale)))))

(defn- paint-sim [^Graphics2D g sim]
  (let [{width :width, height :height} (:window sim)]
    (doto g
      (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
      (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)
      (.setColor (Color. 0 0 0))
      (.setTransform (AffineTransform.))
      (.fillRect 0 0 width height)
      (.setTransform (basic-transform sim)))
    (doseq [element (:drawing sim)]
      (painter/paint-element g element))))

(defn start-paint-listener [dom-view]
  (let [paint-loop (fn []
                     (let [old-state (atom nil)]
                       (loop []
                         (let [new-state @globals/sim-state]
                           (when (not= new-state @old-state)
                             (swap! old-state (fn [_] new-state))
                             (swing.core/invoke-later
                               (println "REPAINT THE THING")
                               (.repaint dom-view))))
                         (Thread/sleep 16)
                         (recur))))]
    (.start (Thread. paint-loop))))

 (defn- create-simulation-view []
    (let [panel (JPanel.)
          pane (JLayeredPane.)
          domView (proxy [JPanel] []
                    (paintComponent [^Graphics2D g]
                      (proxy-super paintComponent g)
                      (paint-sim g @globals/sim-state))
                    (getPreferredSize []
                      (Dimension. 1000 1000)))]
      (start-paint-listener domView)
      (doto domView
        (.setBounds (Rectangle. 0 0 1000 1000)))
      (doto pane
        (.add domView 51)
        (.setPreferredSize (Dimension. 1000 1000)))
      (doto panel
        (.setLayout (BorderLayout.))
        (.setBackground (Color/white))
        (.add pane))))

  (defn start-app []
    (swing.core/invoke-later
      (let [frame (JFrame. "Galactic Simulation")]
        (doto frame
          (.setResizable true)
          (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
          (.setBackground Color/white)
          (.add (create-simulation-view))
          (.pack)
          (.show)))))

