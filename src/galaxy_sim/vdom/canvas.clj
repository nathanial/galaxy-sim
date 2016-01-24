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

(defn- create-compatible-image [width height]
  (let [ge (GraphicsEnvironment/getLocalGraphicsEnvironment)
        gs (.getDefaultScreenDevice ge)
        gc (.getDefaultConfiguration gs)]
    (.createCompatibleImage gc width height Transparency/OPAQUE)))

(defn double-buffer [width height transform paint]
  (let [buffer (create-compatible-image width height)
        graphics (.createGraphics buffer)]
    (doto graphics
      (.setTransform (swing.core/to-affine transform))
      (paint)
      (.dispose))
    buffer))

(defn basic-black-canvas [^Graphics2D g paint]
  (let [state @globals/sim-state
        {width :width, height :height} (:window state)
        transform (:transform state)]
    (doto g
      (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
      (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)
      (.setColor (Color. 0 0 0))
      (.setTransform (AffineTransform.))
      (.fillRect 0 0 width height))
    (let [^BufferedImage image (double-buffer width height transform paint)]
      (.drawImage g image 0 0 nil))))

(defn create [should-repaint paint]
  (let [canvas (swing.core/create-custom-component #(basic-black-canvas %1 paint))]
    (add-watch globals/sim-state :canvas-repaint (repaint-listener should-repaint canvas))
    canvas))