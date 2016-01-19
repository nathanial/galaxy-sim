(ns galaxy-sim.astronomical.star
  (:use clojure.contrib.math)
  (:import [java.util Random]))

(def g2d-transform {:scale {:x 1 :y 1}, :translate {:x 0 :y 0}})

(defn- star-radius [star]
  (let [sx (get-in g2d-transform [:scale :x])]
    (if (< sx 2)
      (/ 1 (expt sx 0.99))
      0.5)))

(defn- calculate-phase-offset [star]
  (let [r (Random. (hash (str (:id star))))]
    (.nextDouble r)))

(defn- star-color [star]
  (let [sx (get-in g2d-transform [:scale :x])
        color (:color star)
        phase-offset (calculate-phase-offset (:id star))]
    (if (< sx 0.3)
      (let [coeff (max (expt (* sx 1000.0), 1.5) 1000.0)
            time (/ (System/currentTimeMillis) (+ coeff (* phase-offset 100)))
            ratio (min (max (* sx 255.0) 100.0) 255.0)
            transparency (+ (* (- 255 ratio) (abs (Math/sin time))) ratio)]
          {:red (:red color)
           :green (:green color)
           :blue (:blue color)
           :alpha transparency})
      color
    )))

(defn draw [star]
  (let [radius (star-radius star)
        color (star-color star)]
    {
      :ellipse {
        :color color
        :vertices [
          (* -1 (:x star))
          (* -1 (:y star))
          (* 2 radius)
          (* 2 radius)
        ]
        :fill true
      }
    }))
