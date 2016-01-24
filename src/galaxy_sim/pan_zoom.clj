(ns galaxy-sim.pan-zoom
  (:use [galaxy-sim.globals :only [sim-state]])
  (:require [swing.core]
            [galaxy-sim.events :as events])
  (:import (java.awt.geom AffineTransform Point2D$Double)))

(defn- update-transform [transform ^AffineTransform at]
  (assoc transform
    :scale {:x (.getScaleX at) :y (.getScaleY at)}
    :translate {:x (.getTranslateX at) :y (.getTranslateY at)}
    ))

(defn- zoom-transform [transform factor]
  (let [{mouseX :x mouseY :y} (:mouse @sim-state)
        at (swing.core/to-affine transform)
        pt (Point2D$Double.)]
    (doto at
      (.inverseTransform (Point2D$Double. mouseX mouseY) pt)
      (.translate (.x pt) (.y pt))
      (.scale factor factor)
      (.translate (- (.x pt)) (- (.y pt))))
    (update-transform transform at)))

(defn zoom [amount]
  (send sim-state
         (fn [{:keys [transform] :as old-state}]
           (let [new-transform (zoom-transform transform amount)]
             (assoc old-state :transform new-transform)))))

(defn zoom-mouse-wheel [event]
  (if (< 0 (:zoom event))
    (zoom 0.9)
    (zoom 1.1)))

(defn pan [{:keys [x y]}]
  (send sim-state
         (fn [{:keys [transform] :as current-state}]
           (let [drag-start (:drag-start current-state)
                 at (swing.core/to-affine (:transform current-state))]
             (doto at
               (.translate (/ (- x (:x drag-start)) (.getScaleX at))
                           (/ (- y (:y drag-start)) (.getScaleY at))))
             (let [new-transform (update-transform transform at)]
               (-> current-state
                   (assoc :transform new-transform)
                   (assoc :drag-start {:x x :y y})))))))

(defn- start-drag [{:keys [x y]}]
  (send sim-state #(assoc %1 :drag-start {:x x :y y})))

(defn- stop-drag [{:keys [x y]}]
  (send sim-state #(assoc %1 :drag-start nil)))

(defn- track-mouse [{:keys [x y]}]
  (send sim-state #(-> %1
                        (assoc-in [:mouse :x] x)
                        (assoc-in [:mouse :y] y))))

(defn resize-window [{:keys [width height]}]
  (send sim-state #(-> %1
                       (assoc-in [:window :width] width)
                       (assoc-in [:window :height] height))))

(defn init []
  (events/add-event-listener :mouse-wheel zoom-mouse-wheel)
  (events/add-event-listener :mouse-move track-mouse)
  (events/add-event-listener :mouse-drag pan)
  (events/add-event-listener :mouse-down start-drag)
  (events/add-event-listener :mouse-up stop-drag)
  (events/add-event-listener :window-resized resize-window))


