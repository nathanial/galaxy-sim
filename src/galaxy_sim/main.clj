(ns galaxy-sim.main
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

(defn- should-repaint [old-state new-state]
  (or (not= (:drawing new-state) (:drawing old-state))
      (not= (:transform new-state) (:transform old-state))
      (not= (:window new-state) (:window old-state))) )

(defn- paint [^Graphics2D g]
  (paint-sim g @globals/sim-state))

(defn redraw-listener [key reference old-state new-state]
  (when (or (not= (:simulation old-state) (:simulation new-state))
            (not= (get-in old-state [:transform :scale])
                  (get-in new-state [:transform :scale])))
    (println "REDRAW")
    (send reference #(assoc %1 :drawing (simulation/draw (:simulation %1))))))

(defn start-game []
  (pan-zoom/init)
  (events/start-event-handler)
  (let [sim (simulation/create)
        drawing (simulation/draw sim)]
    (send globals/sim-state assoc :simulation sim :drawing drawing)
    (add-watch globals/sim-state :redraw redraw-listener)
    (swing.core/invoke-later
      (let [frame (swing.frame/create "Galactic Simulation")
            view (canvas/create should-repaint paint)]
        (.add frame view)))))

(defn -main [& args]
  (start-game))
