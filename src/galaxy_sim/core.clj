(ns galaxy-sim.core
  (:require [galaxy-sim.astronomical.star :as star]
            [galaxy-sim.vdom :as vdom])
  (:gen-class))

(def red {:red 255 :green 0 :blue 0 :alpha 255})

(defn -main [& args]
  (let [s {:x 0 :y 0 :color red}]
    (vdom/render [(star/draw s)])))
