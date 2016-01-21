(ns galaxy-sim.core
  (:require [galaxy-sim.astronomical.star :as star]
            [galaxy-sim.astronomical.simulation :as simulation]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.vdom.swing :as vdom-swing])
  (:gen-class))

(def red {:red 255 :green 0 :blue 0 :alpha 255})

(defn -main [& args]
  (binding [globals/g2d-transform {:scale {:x 0.25 :y 0.25}, :translate {:x 0 :y 0}}]
    (let [sim (simulation/create)]
      (vdom-swing/render (simulation/draw sim)))))
