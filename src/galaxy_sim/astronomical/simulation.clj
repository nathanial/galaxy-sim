(ns galaxy-sim.astronomical.simulation
  (:import [java.util Random])
  (:require [galaxy-sim.astronomical.planet :as planet]
            [galaxy-sim.astronomical.star :as star]))

(def ^:dynamic random nil)

(defn- create-coordinates []
  {
    :x (+ 2500 (* 1000 (.nextGaussian random)))
    :y (+ 2500 (* 1000 (.nextGaussian random)))
  })

(def random-planets
  (iterate (fn [_]
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
    )) 0))

(def random-systems
  (iterate (fn [_]
    (let [coords (create-coordinates)
          planets (take (rand-int 9) random-planets)
          asteroids []]
        {
          :star {:color [255 0 0]}
          :coords coords
          :planets planets
          :asteroids asteroids
          })) 0))

(defn create []
  (binding [random (Random.)]
    (take 1000 random-systems)))

(defn draw [sim]
  [])
