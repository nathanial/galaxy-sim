(ns galaxy-sim.astronomical.star
  (:use clojure.contrib.math)
  (:require [galaxy-sim.globals :as globals])
  (:import [java.util Random]))


(defn- star-radius [star]
  (let [sx (get-in @globals/sim-state [:transform :scale :x])]
    (if (< sx 2)
      (/ 1 (expt sx 0.99))
      0.5)))

(defn draw [star]
  (let [radius (star-radius star)
        color (:color star)]
    {
      :type :ellipse
      :color color
      :radius radius
      :vertices [
        (- (:x star) radius)
        (- (:y star) radius)
        (* 2 radius)
        (* 2 radius)
      ]
      :fill true
    }))
