(ns galaxy-sim.astronomical.planet)

(defn in?
  "true if seq contains elm"
  [seq elm]
  (some #(= elm %) seq))

(def kinds [
  {
    :kind :earth,
    :color [45 136 45]
  }
  {
    :kind :desert
    :color [233 193 132]
  }
  {
    :kind :oceanic
    :color [43 75 111]
  }
  {
    :kind :volcanic
    :color [107 11 11]
  }
  {
    :kind :barren
    :color [107 69 11]
  }
  {
    :kind :arctic
    :color [171 189 209]
  }
  {
    :kind :gas-giant
    :color [167 116 87]
  }
])

(defn size [random kind]
  (cond
    (in? [:earth, :desert, :oceanic, :volcanic, :barren, :arctic] kind)
    (+ 0.5 (* 0.1 (.nextDouble random)))

    :else
    (+ 0.2 (* 0.1 (.nextDouble random)))))
