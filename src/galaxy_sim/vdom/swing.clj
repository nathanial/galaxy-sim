(ns galaxy-sim.vdom.swing
  (:require [galaxy-sim.vdom.painter :as painter]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.vdom.canvas :as canvas]
            [swing.core]
            [swing.frame]
            [galaxy-sim.events :as events])
  (:import [java.awt Color RenderingHints Graphics2D]
           [java.awt.geom AffineTransform]))

(defn basic-transform [sim]
  (let [transform (AffineTransform. )
        {:keys [scale translate]} (:transform sim)]
    (doto transform
      (.translate (:x translate) (:y translate))
      (.scale (:x scale) (:y scale)))))

(defn- paint-sim [^Graphics2D g sim]
  (let [{width :width, height :height} (:window @globals/sim-state)]
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
  (events/add-event-listener
    :window-resized
     (fn [{:keys [width height]}]
       (send globals/sim-state #(-> %1
                                     (assoc-in [:window :width] width)
                                     (assoc-in [:window :height] height)))))
  (let [should-repaint (fn [old-state new-state]
                         (or (not= (:drawing new-state) (:drawing old-state))
                             (not= (:transform new-state) (:transform old-state))
                             (not= (:window new-state) (:window old-state))))
        paint (fn [^Graphics2D g]
                (let [new-state @globals/sim-state]
                  (paint-sim g new-state)))]
    (swing.core/invoke-later
      (let [frame (swing.frame/create "Galactic Simulation")
            view (canvas/create should-repaint paint)]
        (.add frame view)))))