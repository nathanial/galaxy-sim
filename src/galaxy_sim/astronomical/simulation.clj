(ns galaxy-sim.astronomical.simulation
  (:import [java.util Random])
  (:require [galaxy-sim.astronomical.planet :as planet]
            [galaxy-sim.astronomical.star :as star]))

(def random (Random. ))

(defn- create-coordinates []
  {
    :x (+ 2500 (* 1000 (.nextGaussian random)))
    :y (+ 2500 (* 1000 (.nextGaussian random)))
  })

(def random-planets
  (repeatedly (fn []
    (let [radius (+ 1.5 (.nextDouble random))
          angle (* 360 (.nextDouble random))
          kind (rand-nth planet/kinds)
          size (planet/size random kind)
          speed (/ (max (.nextDouble random) 0.5) radius)]
      {
        :radius radius
        :angle angle
        :kind kind
        :size size
        :speed speed
      }
    ))))

(def random-systems
  (repeatedly (fn []
    (let [coords (create-coordinates)
          planets (take (rand-int 9) random-planets)
          asteroids []]
        {
          :star {:color (rand-nth star/colors), :x (:x coords) :y (:y coords)}
          :planets planets
          :asteroids asteroids
          }))))

(defn create []
  (take 100000 random-systems))

(defn draw [sim]
  (for [system sim]
    (star/draw (:star system))))
