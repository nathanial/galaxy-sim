(ns galaxy-sim.astronomical.star
  (:use clojure.contrib.math))


(def g2d-transform {:scale {:x 1 :y 1}, :translate {:x 0 :y 0}})

(defn- star-radius [star]
  (let [sx (get-in g2d-transform [:scale :x])]
    (if (< sx 2)
      (/ 1 (expt sx 0.99))
      0.5)))

(defn draw [star]
  (let [radius (star-radius star)]
    {
      :ellipse {
        :color (:color star)
        :vertices [
          (* -1 (:x star))
          (* -1 (:y star))
          (* 2 radius)
          (* 2 radius)
        ]
        :fill true
      }
    }))
