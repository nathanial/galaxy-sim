(ns swing.frame
  (:import (javax.swing JFrame)
           (java.awt Color)
           (java.awt.event MouseWheelListener MouseMotionAdapter ComponentAdapter)
           (javax.swing.event MouseInputAdapter))
  (:use [swing.core]))

(defn add-listeners [frame]
  (doto frame
    (.addMouseWheelListener
      (proxy [MouseWheelListener] []
        (mouseWheelMoved [e]
          (.add event-queue {:event :mouse-wheel :zoom (* (.getWheelRotation e) (.getScrollAmount e))}))))
    (.addMouseMotionListener
      (proxy [MouseMotionAdapter] []
        (mouseMoved [e]
          (.add event-queue {:event :mouse-move :x (.getX e) :y (.getY e)}))
        (mouseDragged [e]
          (.add event-queue {:event :mouse-drag :x (.getX e) :y (.getY e)}))))
    (.addMouseListener
      (proxy [MouseInputAdapter] []
        (mousePressed [e]
          (.add event-queue {:event :mouse-down, :x (.getX e), :y (.getY e)}))
        (mouseReleased [e]
          (.add event-queue {:event :mouse-up, :x (.getX e), :y (.getY e)}))))
    (.addComponentListener
      (proxy [ComponentAdapter] []
        (componentResized [e]
          (let [width (.. frame size width)
                height (.. frame size height)]
            (.add event-queue {:event  :window-resized :width width :height height})))
        (componentMoved [e]
          (let [width (.. frame size width)
                height (.. frame size height)]
            (.add event-queue {:event :window-moved :width width :height height})))))))

(defn create [title]
  (let [frame (JFrame. title)]
    (doto frame
      (.setResizable true)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setBackground Color/white)
      (add-listeners)
      (.pack)
      (.show))))