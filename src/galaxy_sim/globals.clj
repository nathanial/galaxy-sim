(ns galaxy-sim.globals
  (:import [java.util.concurrent ConcurrentLinkedQueue]))

(def sim-state (atom {
  :transform {:scale {:x 0.25 :y 0.25}, :translate {:x 0 :y 0}}
  :simulation []
  :drawing []
}))

(def event-queue (ConcurrentLinkedQueue.))
