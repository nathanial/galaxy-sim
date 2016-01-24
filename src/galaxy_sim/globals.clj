(ns galaxy-sim.globals)

(def sim-state (atom {
  :transform {:scale {:x 1.0 :y 1.0}, :translate {:x 0 :y 0}}
  :simulation []
  :drawing []
}))
