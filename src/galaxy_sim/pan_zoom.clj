(ns galaxy-sim.pan-zoom
  (:use [galaxy-sim.globals :only [sim-state]])
  (:require [swing.core]))

(defn- zoom-transform [transform factor]
  (let [{sx :x sy :y} (:scale transform)
        {ox :x oy :y} (:translate transform)
        ; x (- ox (* anchorX deltaWidth))
        ; y (- oy (* anchorY deltaHeight))
        ]
    (assoc transform
      :scale {:x (* sx factor) :y (* sy factor)}
      :translate {:x ox :y oy}
      )))

(defn zoom [amount]
  (swap! sim-state
         (fn [{:keys [transform] :as old-state}]
           (let [new-transform (zoom-transform transform amount)]
             (assoc old-state :transform new-transform)))))

(defn zoom-mouse-wheel [event]
  (if (< 0 (:zoom event))
    (zoom 0.9)
    (zoom 1.1)))

(defn init []
  (swing.core/add-event-listener :mouse-wheel zoom-mouse-wheel))

