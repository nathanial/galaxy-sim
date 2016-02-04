(ns galaxy-sim.main
  (:require [galaxy-sim.astronomical.simulation :as simulation]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.pan-zoom :as pan-zoom]
            [galaxy-sim.events :as events]
            [swing.frame]
            [galaxy-sim.vdom.painter :as painter]
            [galaxy-sim.vdom.tile-renderer :as tile-renderer])
  (:gen-class)
  (:import (java.awt Graphics2D RenderingHints Color)
           (javax.swing JPanel)
           (java.awt.image BufferedImage)))

(defn- paint-tiles [^Graphics2D g transform tiles]
  (let [scale (:scale transform)]
    (doseq [tile tiles]
      (let [^BufferedImage image (:image tile)
            transform (painter/create-transform scale (:translate transform) (:translate tile))]
        (doto g
          (.setTransform transform)
          (.drawImage image 0 0 nil)
          (.setColor Color/red)
          (.drawRect 0 0 (dec tile-renderer/tile-width) (dec tile-renderer/tile-height)))))))

(defn- paint-sim [^Graphics2D g]
  (let [sim @globals/sim-state
        transform (:transform sim)
        tiles (tile-renderer/render-as-tiles transform (:window sim) (:drawing sim))]
    (doto g
      (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
      (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY))
    (paint-tiles g transform tiles)))

(defn- should-repaint [old-state new-state]
  (or (not= (:drawing new-state) (:drawing old-state))
      (not= (:transform new-state) (:transform old-state))
      (not= (:window new-state) (:window old-state))) )

(defn redraw-listener [key reference old-state new-state]
  (when (or (not= (:simulation old-state) (:simulation new-state)))
    (send reference #(assoc %1 :drawing (simulation/draw (:simulation %1))))))

(defn- repaint-listener [^JPanel canvas]
  (fn [key reference old-state new-state]
    (when (should-repaint old-state new-state)
      (swing.core/invoke-later
        (.repaint canvas)))))

(defn start-game []
  (pan-zoom/init)
  (events/start-event-handler)
  (let [sim (simulation/create)
        drawing (simulation/draw sim)]
    (send globals/sim-state assoc :simulation sim :drawing drawing)
    (add-watch globals/sim-state :redraw redraw-listener)
    (swing.core/invoke-later
      (let [frame (swing.frame/create "Galactic Simulation")
            view (swing.core/create-custom-component paint-sim)]
        (add-watch globals/sim-state :canvas-repaint (repaint-listener view))
        (.add frame view)))))

(defn -main [& args]
  (start-game))
