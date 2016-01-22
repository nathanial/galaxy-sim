(ns galaxy-sim.core
  (:require [galaxy-sim.astronomical.star :as star]
            [galaxy-sim.astronomical.simulation :as simulation]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.vdom.swing :as vdom-swing])
  (:gen-class))

(def red {:red 255 :green 0 :blue 0 :alpha 255})

(defn handle-events []
  (loop []
    (let [event (.poll globals/event-queue)]
      (when event
        (println event))
      (recur))))

(defn -main [& args]
  (.start (Thread. handle-events))
  (let [sim (simulation/create)
        drawing (simulation/draw sim)]
    (dosync
      (swap! globals/sim-state assoc
             :simulation sim
             :drawing drawing))
    (vdom-swing/render (:drawing @globals/sim-state))))
