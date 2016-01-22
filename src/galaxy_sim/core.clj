(ns galaxy-sim.core
  (:require [galaxy-sim.astronomical.star :as star]
            [galaxy-sim.astronomical.simulation :as simulation]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.vdom.swing :as vdom-swing])
  (:gen-class))

(defn handle-events []
  (loop []
    (let [event (.poll globals/event-queue)]
      (when event
        (let [kind (:event event)]
          (doseq [listener (kind @globals/event-listeners)]
            (listener event))))
      (recur))))

(globals/add-event-listener :mouse-move (fn [e] (println "Mouse Move" e)))

(defn -main [& args]
  (.start (Thread. handle-events))
  (let [sim (simulation/create)
        drawing (simulation/draw sim)]
    (swap! globals/sim-state assoc
           :simulation sim
           :drawing drawing)
    (vdom-swing/render (:drawing @globals/sim-state))))
