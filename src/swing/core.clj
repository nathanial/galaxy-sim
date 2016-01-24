(ns swing.core
  (:import (javax.swing SwingUtilities JPanel)
           (java.awt Graphics2D))
  (:use [com.rpl.specter]))

(defmacro invoke-later [& args]
  `(SwingUtilities/invokeLater (fn [] ~@args)))


(defn create-custom-component [paint]
  (proxy [JPanel] []
    (paintComponent [^Graphics2D g]
      (proxy-super paintComponent g)
      (paint g))))