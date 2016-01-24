(ns util.core)

(defn spawn-thread [^Runnable func]
  (.start (Thread. func)))
