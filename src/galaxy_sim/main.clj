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

(defn- paint-sim [^Graphics2D g]
  (let [sim @globals/sim-state
        transform (:transform sim)
        scale (:scale transform)
        tiles (tile-renderer/render-as-tiles transform (:window sim) (:drawing sim))]
    (swing.core/set-graphic-defaults g)
    (doseq [tile tiles]
      (let [^BufferedImage image (:image tile)
            transform (painter/create-transform scale (:translate transform) (:translate tile))]
        (doto g
          (.setTransform transform)
          (.drawImage image 0 0 nil)
          (.setColor Color/red)
          (.drawRect 0 0 (dec tile-renderer/tile-width) (dec tile-renderer/tile-height)))))))

(defn- should-repaint [old-state new-state]
  (or (not= (:drawing new-state) (:drawing old-state))
      (not= (:transform new-state) (:transform old-state))
      (not= (:window new-state) (:window old-state))))

(defn start-game []
  (pan-zoom/init)
  (events/start-event-handler)
  (let [sim (simulation/create)
        drawing (simulation/draw sim)]
    (send globals/sim-state assoc :simulation sim :drawing drawing)
    (add-watch globals/sim-state :redraw
               (fn [key reference old-state new-state]
                 (when (or (not= (:simulation old-state) (:simulation new-state)))
                   (send reference #(assoc %1 :drawing (simulation/draw (:simulation %1)))))))
    (swing.core/invoke-later
      (let [frame (swing.frame/create "Galactic Simulation")
            view (swing.core/create-custom-component paint-sim)]
        (add-watch globals/sim-state :canvas-repaint
                   (fn [key reference old-state new-state]
                     (when (should-repaint old-state new-state)
                       (swing.core/invoke-later
                         (.repaint view)))))
        (.add frame view)))))

(defn -main [& args]
  (start-game))
