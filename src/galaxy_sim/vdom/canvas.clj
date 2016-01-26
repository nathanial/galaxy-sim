(ns galaxy-sim.vdom.canvas
  (:require [util.core]
            [swing.core]
            [galaxy-sim.globals :as globals])
  (:import (javax.swing JPanel)
           (java.awt Graphics2D RenderingHints Color Component GraphicsConfiguration GraphicsEnvironment Transparency)
           (java.awt.geom AffineTransform)
           (java.awt.image BufferedImage)))

(defn- repaint-listener [should-repaint ^JPanel canvas]
  (fn [key reference old-state new-state]
    (when (should-repaint old-state new-state)
      (swing.core/invoke-later
        (.repaint canvas)))))

(defn basic-black-canvas [^Graphics2D g paint]
  (let [state @globals/sim-state
        {width :width, height :height} (:window state)]
    (doto g
      (.setColor (Color. 0 0 0))
      (.setTransform (AffineTransform.))
      (.fillRect 0 0 width height))
    (paint g)))

(defn create [should-repaint paint]
  (let [canvas (swing.core/create-custom-component #(basic-black-canvas %1 paint))]
    (add-watch globals/sim-state :canvas-repaint (repaint-listener should-repaint canvas))
    canvas))