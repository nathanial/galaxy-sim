(ns galaxy-sim.vdom.canvas
  (:require [util.core]
            [swing.core]
            [galaxy-sim.globals :as globals])
  (:import (javax.swing JPanel)))

(defn- repaint-listener [should-repaint ^JPanel canvas]
  (fn [key reference old-state new-state]
    (when (should-repaint old-state new-state)
      (swing.core/invoke-later
        (.repaint canvas)))))

(defn create [should-repaint paint]
  (let [canvas (swing.core/create-custom-component paint)]
    (add-watch globals/sim-state :canvas-repaint (repaint-listener should-repaint canvas))
    canvas))