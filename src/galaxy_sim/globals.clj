(ns galaxy-sim.globals)

(def sim-state (agent {
  :transform {:scale {:x 1.0 :y 1.0}, :translate {:x 0 :y 0}}
  :simulation []
  :drawing []
  :window {:width 1000, :height 1000}
  :mouse {:x 0 :y 0}
}))
