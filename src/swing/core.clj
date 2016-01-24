(ns swing.core
  (:import (javax.swing SwingUtilities JPanel)
           (java.util.concurrent LinkedBlockingQueue)
           (java.awt Graphics2D))
  (:use [com.rpl.specter]))

(defmacro invoke-later [& args]
  `(SwingUtilities/invokeLater (fn [] ~@args)))

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


(defn create-custom-component [paint]
  (proxy [JPanel] []
    (paintComponent [^Graphics2D g]
      (proxy-super paintComponent g)
      (paint g))))