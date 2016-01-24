(ns swing.core
  (:import (javax.swing SwingUtilities JPanel)
           (java.util.concurrent LinkedBlockingQueue)
           (java.awt Graphics2D))
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

(def window-state (atom {
 :window {:width 1000, :height 1000}
 :mouse {:x 0, :y 0, :pressed false}
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

(add-event-listener
  :mouse-move
  (fn [{:keys [x y]}]
    (swap! window-state #(-> %1
                             (assoc-in [:mouse :x] x)
                             (assoc-in [:mouse :y] y)))))

(defn create-custom-component [paint]
  (proxy [JPanel] []
    (paintComponent [^Graphics2D g]
      (proxy-super paintComponent g)
      (paint g))))