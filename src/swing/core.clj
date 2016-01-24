(ns swing.core
  (:import (javax.swing SwingUtilities JPanel)
           (java.awt Graphics2D)
           (java.awt.geom AffineTransform))
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