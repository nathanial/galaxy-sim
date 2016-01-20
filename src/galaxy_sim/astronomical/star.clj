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

(defn draw [star]
  (let [radius (star-radius star)
        color (:color star)]
    {
      :type :ellipse
      :color color
      :radius radius
      :vertices [
        (* -1 (:x star))
        (* -1 (:y star))
        (* 2 radius)
        (* 2 radius)
      ]
      :fill true
    }))
