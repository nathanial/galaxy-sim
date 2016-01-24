(ns galaxy-sim.events
  (:import (java.util.concurrent LinkedBlockingQueue)))

(def ^LinkedBlockingQueue event-queue (LinkedBlockingQueue.))

(def event-listeners (atom {}))

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
  (swap! event-listeners
         (fn [listeners]
           (let [kl (kind listeners)]
             (if (not kl)
               (assoc listeners kind [func])
               (assoc listeners kind (conj kl func)))))))

