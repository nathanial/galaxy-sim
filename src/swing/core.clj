(ns swing.core
  (:import (javax.swing SwingUtilities JPanel)
           (java.awt Graphics2D GraphicsEnvironment Transparency RenderingHints)
           (java.awt.geom AffineTransform)
           (java.awt.image BufferedImage))
  (:use [com.rpl.specter]))

(defmacro invoke-later [& args]
  `(SwingUtilities/invokeLater (fn [] ~@args)))

(defn to-affine [{:keys [translate scale]}]
  (let [at (AffineTransform.)
        {tx :x ty :y} translate
        {sx :x sy :y} scale]
    (doto at
      (.translate tx ty)
      (.scale sx sy))))

(defn create-custom-component [paint]
  (proxy [JPanel] []
    (paintComponent [^Graphics2D g]
      (proxy-super paintComponent g)
      (paint g))))


(defn ^BufferedImage create-compatible-image [width height]
  (let [ge (GraphicsEnvironment/getLocalGraphicsEnvironment)
        gs (.getDefaultScreenDevice ge)
        gc (.getDefaultConfiguration gs)]
    (.createCompatibleImage gc width height Transparency/OPAQUE)))

(defn add-translations [& translations]
  (let [new-transform {:scale     {:x 1 :y 1}
                       :translate {:x (apply + (map :x translations))
                                   :y (apply + (map :y translations))}}]
    (to-affine new-transform)))

(defn set-graphic-defaults [^Graphics2D g]
  (doto g
    (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
    (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)))