(ns galaxy-sim.astronomical.star
  (:use clojure.contrib.math)
  (:require [galaxy-sim.globals :as globals])
  (:import (java.io File)
           (javax.imageio ImageIO)))

(defn- load-colors []
  (let [file (File. "resources/StarColorGradient.png")
        image (ImageIO/read file)]
    (for [x (range 0 200)]
      (let [clr (.getRGB image x 0)
            red (bit-shift-right (bit-and clr 0x00ff0000) 16)
            green (bit-shift-right (bit-and clr 0x0000ff00) 8)
            blue (bit-and clr 0x000000ff)]
        [red green blue]))))

(def colors (load-colors))

(defn draw [star]
  (let [radius 0.5
        color (:color star)]
    {
      :type :circle
      :color color
      :radius radius
      :x (:x star)
      :y (:y star)
      :fill true
    }))
