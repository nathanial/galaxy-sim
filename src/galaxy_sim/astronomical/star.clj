(ns galaxy-sim.astronomical.star
  (:use clojure.contrib.math)
  (:require [galaxy-sim.globals :as globals])
  (:import (java.io File)
           (javax.imageio ImageIO)))

(defn- load-colors []
  (let [file (File. "resources/StarColorGradient.png")
        image (ImageIO/read file)]
    (doall
      (for [x (range 0 200)]
        (let [clr (.getRGB image x 0)
              red (bit-shift-right (bit-and clr 0x00ff0000) 16)
              green (bit-shift-right (bit-and clr 0x0000ff00) 8)
              blue (bit-and clr 0x000000ff)]
          [red green blue])))))

(def colors (load-colors))

(defn- star-radius [_]
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
