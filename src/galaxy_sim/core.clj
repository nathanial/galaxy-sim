(ns galaxy-sim.core
  (:require [galaxy-sim.astronomical.star :as star]
            [galaxy-sim.astronomical.simulation :as simulation]
            [galaxy-sim.vdom.swing :as vdom-swing])
  (:gen-class))

(def red {:red 255 :green 0 :blue 0 :alpha 255})

(defn -main [& args]
  (let [sim (simulation/create)]
    (vdom-swing/render (simulation/draw sim))))
