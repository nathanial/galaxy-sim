(ns galaxy-sim.vdom.canvas
  (:require [util.core]
            [swing.core]
            [galaxy-sim.globals :as globals])
  (:import (javax.swing JPanel)
           (java.awt Graphics2D RenderingHints Color)
           (java.awt.geom AffineTransform)))

(defn- repaint-listener [should-repaint ^JPanel canvas]
  (fn [key reference old-state new-state]
    (when (should-repaint old-state new-state)
      (swing.core/invoke-later
        (.repaint canvas)))))

(defn basic-black-canvas [^Graphics2D g paint]
  (let [state @globals/sim-state
        {width :width, height :height} (:window state)
        transform (:transform state)]
    (doto g
      (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
      (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)
      (.setColor (Color. 0 0 0))
      (.setTransform (AffineTransform.))
      (.fillRect 0 0 width height)
      (.setTransform (swing.core/to-affine transform)))
    (paint g)))

(defn create [should-repaint paint]
  (let [canvas (swing.core/create-custom-component #(basic-black-canvas %1 paint))]
    (add-watch globals/sim-state :canvas-repaint (repaint-listener should-repaint canvas))
    canvas))