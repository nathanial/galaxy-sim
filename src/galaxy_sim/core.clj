(ns galaxy-sim.core
  (:require [galaxy-sim.astronomical.simulation :as simulation]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.pan-zoom :as pan-zoom]
            [galaxy-sim.vdom.swing :as vdom-swing]
            [galaxy-sim.events :as events]
            [swing.frame])
  (:gen-class))

(defn start-game []
  (pan-zoom/init)
  (events/start-event-handler)
  (let [sim (simulation/create)
        drawing (simulation/draw sim)]
    (send globals/sim-state assoc
          :simulation sim
          :drawing drawing)
    (vdom-swing/start-app)))

(defn -main [& args]
  (start-game))
