(ns galaxy-sim.core
  (:require [galaxy-sim.astronomical.simulation :as simulation]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.pan-zoom :as pan-zoom]
            [galaxy-sim.events :as events]
            [swing.frame]
            [galaxy-sim.vdom.painter :as painter]
            [galaxy-sim.vdom.canvas :as canvas])
  (:gen-class)
  (:import (java.awt Graphics2D)))

(defn- paint-sim [^Graphics2D g sim]
  (doseq [element (:drawing sim)]
    (painter/paint-element g element)))

(defn start-game []
  (pan-zoom/init)
  (events/start-event-handler)
  (let [sim (simulation/create)
        drawing (simulation/draw sim)]
    (send globals/sim-state assoc :simulation sim :drawing drawing)
    (events/add-event-listener :window-resized
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
          (.add frame view))))))

(defn -main [& args]
  (start-game))
