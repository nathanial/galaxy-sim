(ns galaxy-sim.vdom.canvas
  (:require [util.core] [swing.core]))

(defn- start-repaint-listener [should-repaint canvas]
  (util.core/spawn-thread
    (fn []
      (loop []
        (when (should-repaint)
          (swing.core/invoke-later
            (.repaint canvas)))
        (Thread/sleep 16)
        (recur)))))

(defn create [should-repaint paint]
  (let [canvas (swing.core/create-custom-component paint)]
    (start-repaint-listener should-repaint canvas)
    canvas))