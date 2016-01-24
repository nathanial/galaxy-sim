(ns galaxy-sim.pan-zoom
  (:use [galaxy-sim.globals :only [sim-state]])
  (:require [swing.core])
  (:import (java.awt.geom AffineTransform Point2D$Double)))

(defn- create-affine-transform [transform]
  (let [at (AffineTransform.)
        {tx :x ty :y} (:translate transform)
        {sx :x sy :y} (:scale transform)]
    (doto at
      (.translate tx ty)
      (.scale sx sy))))

(defn- update-transform [transform ^AffineTransform at]
  (assoc transform
    :scale {:x (.getScaleX at) :y (.getScaleY at)}
    :translate {:x (.getTranslateX at) :y (.getTranslateY at)}
    ))

(defn- zoom-transform [transform factor]
  (let [{mouseX :x mouseY :y} (:mouse @swing.core/window-state)
        at (create-affine-transform transform)
        pt (Point2D$Double.)]
    (doto at
      (.inverseTransform (Point2D$Double. mouseX mouseY) pt)
      (.translate (.x pt) (.y pt))
      (.scale factor factor)
      (.translate (- (.x pt)) (- (.y pt))))
    (update-transform transform at)))

(defn zoom [amount]
  (swap! sim-state
         (fn [{:keys [transform] :as old-state}]
           (let [new-transform (zoom-transform transform amount)]
             (assoc old-state :transform new-transform)))))

(defn zoom-mouse-wheel [event]
  (if (< 0 (:zoom event))
    (zoom 0.9)
    (zoom 1.1)))

(defn pan [{:keys [x y]}]
  (let [drag-start (:drag-start @swing.core/window-state)
        at (create-affine-transform (:transform @sim-state))]
    (doto at
      (.translate (/ (- x (:x drag-start)) (.getScaleX at))
                  (/ (- y (:y drag-start)) (.getScaleY at))))
    (swap! swing.core/window-state #(assoc %1 :drag-start {:x x :y y}))
    (swap! sim-state
           (fn [{:keys [transform] :as old-state}]
             (let [new-transform (update-transform transform at)]
               (assoc old-state :transform new-transform))))))

(defn start-drag [{:keys [x y]}]
  (swap! swing.core/window-state #(assoc %1 :drag-start {:x x :y y})))

(defn init []
  (swing.core/add-event-listener :mouse-wheel zoom-mouse-wheel)
  (swing.core/add-event-listener :mouse-drag pan)
  (swing.core/add-event-listener :mouse-down start-drag))

