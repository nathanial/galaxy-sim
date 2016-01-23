(ns galaxy-sim.vdom.swing
  (:require [galaxy-sim.vdom.painter :as painter]
            [galaxy-sim.globals :as globals])
  (:use [galaxy-sim.events :only [event-queue]])
  (:import [javax.swing
            SwingUtilities JFrame JPanel JLayeredPane]
           [javax.swing.event MouseInputAdapter]
           [java.awt Color BorderLayout Dimension RenderingHints Rectangle Graphics2D]
           [java.awt.event MouseWheelListener MouseMotionAdapter ComponentAdapter]
           [java.awt.geom AffineTransform]))

(defmacro invoke-later [& args]
  `(SwingUtilities/invokeLater (fn [] ~@args)))

(defn basic-transform []
  (let [transform (AffineTransform. )]
    (doto transform
      (.translate 0.0 0.0)
      (.scale 0.25 0.25))))

(defn- create-dom-view [dom]
  (proxy [JPanel] []
    (paintComponent [^Graphics2D g]
      (proxy-super paintComponent g)
      (doto g
        (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
        (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)
        (.setColor (Color. 0 0 0))
        (.setTransform (AffineTransform.))
        (.fillRect 0 0 (.. this (getSize) width) (.. this (getSize) height))
        (.setTransform (basic-transform)))
      (doseq [element dom]
        (painter/paint-element g element)))
    (getPreferredSize  []
      (Dimension. 1000 1000))))

(defn- create-simulation-view []
  (let [panel (JPanel. )
        pane (JLayeredPane.)
        domView (create-dom-view (:drawing @globals/sim-state))]
    (doto domView
      (.setBounds (Rectangle. 0 0 1000 1000)))
    (doto pane
      (.add domView 51)
      (.setPreferredSize (Dimension. 1000 1000)))
    (doto panel
      (.setLayout (BorderLayout. ))
      (.setBackground (Color/white))
      (.add pane)
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
            (let [width (.. panel size width)
                  height (.. panel size height)]
              (.add event-queue {
                :event :window-resized
                :width width
                :height height})
              (.setBounds domView (Rectangle. 0 0 width height))))
          (componentMoved [e]
            (let [width (.. panel size width)
                  height (.. panel size height)]
              (.add event-queue {
                :event :window-moved
                :width width
              })
              (.setBounds domView (Rectangle. 0 0 width height))))))
      )))

(defn paint-loop []
  (let [old-state (atom nil)]
    (loop []
      (let [new-state @globals/sim-state]
        (when (not= new-state @old-state)
          (swap! old-state (fn [_] new-state))
          ))
      (Thread/sleep 16)
      (recur))))


(defn start-paint-listener []
  (.start (Thread. paint-loop)))

(defn start-app []
  (invoke-later
    (let [frame (JFrame. "Galactic Simulation")]
      (doto frame
        (.setResizable true)
        (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
        (.setBackground Color/white)
        (.add (create-simulation-view))
        (.pack)
        (.show)))
    (start-paint-listener)))

