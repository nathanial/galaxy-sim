(ns galaxy-sim.core
  (:use seesaw.core)
  (:require [galaxy-sim.astronomical.star :as star])
  (:gen-class))

(defn -main [& args]
  (let [s {:x 0 :y 0 :color [255 255 255]}]
    (println "Star" (star/draw s))))

    ; (invoke-later
    ;   (-> (frame :title "Hello",
    ;          :content "Hello, Seesaw",
    ;          :on-close :exit)
    ;    pack!
    ;    show!)))
