(ns galaxy-sim.globals
  (:import [java.util.concurrent ConcurrentLinkedQueue
                                 LinkedBlockingQueue])
  (:use [com.rpl.specter]))

(def sim-state (atom {
  :transform {:scale {:x 0.25 :y 0.25}, :translate {:x 0 :y 0}}
  :simulation []
  :drawing []
  :window {:width 1000, :height 1000}
}))

(def event-queue (LinkedBlockingQueue.))

(def event-listeners (atom {
  :mouse-move []
  :mouse-drag []
  :mouse-wheel []
  :mouse-up []
  :mouse-down []
  :window-resized []
  :window-moved []
}))

(defn add-event-listener [kind func]
  (swap! event-listeners #(setval [kind END] [func] %1)))
