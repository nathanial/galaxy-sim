(ns galaxy-sim.vdom.swing
  (:require [galaxy-sim.vdom.painter :as painter]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.vdom.canvas :as canvas]
            [swing.core]
            [swing.frame])
  (:import [java.awt Color RenderingHints Graphics2D]
           [java.awt.geom AffineTransform]))

(defn basic-transform [sim]
  (let [transform (AffineTransform. )
        {:keys [scale translate]} (:transform sim)]
    (doto transform
      (.translate (:x translate) (:y translate))
      (.scale (:x scale) (:y scale)))))

(defn- paint-sim [^Graphics2D g sim]
  (let [{width :width, height :height} (:window @swing.core/window-state)]
    (doto g
      (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
      (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)
      (.setColor (Color. 0 0 0))
      (.setTransform (AffineTransform.))
      (.fillRect 0 0 width height)
      (.setTransform (basic-transform sim)))
    (doseq [element (:drawing sim)]
      (painter/paint-element g element))))

(defn start-app []
  (let [old-state (atom nil)
        should-repaint #(not= @globals/sim-state @old-state)
        paint (fn [^Graphics2D g]
                (let [new-state @globals/sim-state]
                  (swap! old-state (fn [_] new-state))
                  (paint-sim g new-state)))]
    (swing.core/invoke-later
      (let [frame (swing.frame/create "Galactic Simulation")
            view (canvas/create should-repaint paint)]
        (.add frame view)))))

