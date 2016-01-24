(ns galaxy-sim.core
  (:require [galaxy-sim.astronomical.simulation :as simulation]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.pan-zoom :as pan-zoom]
            [galaxy-sim.vdom.swing :as vdom-swing]
            [swing.frame])
  (:gen-class))

(defn start-game []
  (pan-zoom/init)
  (swing.core/start-event-handler)
  (let [sim (simulation/create)
        drawing (simulation/draw sim)]
    (swap! globals/sim-state assoc
           :simulation sim
           :drawing drawing)
    (vdom-swing/start-app)))

(defn -main [& args]
  (swing.core/start-event-handler)
  (swing.core/add-event-listener :mouse-move #(println "Mouse Move" %1))
  (swing.frame/create "Test"))
