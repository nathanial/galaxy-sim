(ns swing.core
  (:import (javax.swing SwingUtilities)
           (java.util.concurrent LinkedBlockingQueue))
  (:use [com.rpl.specter]))

(defmacro invoke-later [& args]
  `(SwingUtilities/invokeLater (fn [] ~@args)))

(def ^LinkedBlockingQueue event-queue (LinkedBlockingQueue.))

(def event-listeners (atom {
  :mouse-move []
  :mouse-drag []
  :mouse-wheel []
  :mouse-up []
  :mouse-down []
  :window-resized []
  :window-moved []
}))

(defn- handle-events []
  (loop []
    (let [event (.poll event-queue)]
      (when event
        (let [kind (:event event)]
          (doseq [listener (kind @event-listeners)]
            (listener event))))
      (recur))))

(defn start-event-handler []
  (.start (Thread. handle-events)))

(defn add-event-listener [kind func]
  (swap! event-listeners #(setval [kind END] [func] %1)))
