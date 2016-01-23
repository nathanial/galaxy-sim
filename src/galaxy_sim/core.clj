(ns galaxy-sim.core
  (:require [galaxy-sim.astronomical.simulation :as simulation]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.events :as events]
            [galaxy-sim.pan-zoom :as pan-zoom]
            [galaxy-sim.vdom.swing :as vdom-swing])
  (:gen-class))


(defn -main [& args]
  (pan-zoom/init)
  (events/start-event-handler)
  (let [sim (simulation/create)
        drawing (simulation/draw sim)]
    (swap! globals/sim-state assoc
           :simulation sim
           :drawing drawing)
    (vdom-swing/render (:drawing @globals/sim-state))))
