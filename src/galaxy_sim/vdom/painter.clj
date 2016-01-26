(ns galaxy-sim.vdom.painter
  (:import [java.awt Color Graphics2D RenderingHints GraphicsEnvironment Transparency]
           [java.awt.geom Ellipse2D$Double AffineTransform]))


(defn- create-compatible-image [width height]
  (let [ge (GraphicsEnvironment/getLocalGraphicsEnvironment)
        gs (.getDefaultScreenDevice ge)
        gc (.getDefaultConfiguration gs)]
    (.createCompatibleImage gc width height Transparency/OPAQUE)))

(defn- double-buffer [width height transform paint]
  (let [buffer (create-compatible-image width height)
        graphics (.createGraphics buffer)]
    (doto graphics
      (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
      (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)
      (.setTransform (swing.core/to-affine transform))
      (paint)
      (.dispose))
    buffer))

(defmulti paint-element (fn [_ element] (:type element)))

(defmethod paint-element :ellipse [^Graphics2D graphics element]
  (let [[^Integer red ^Integer green ^Integer blue alpha?] (:color element)
        ^Integer alpha (if (nil? alpha?) 255 alpha?)
        [a b c d] (:vertices element)
        shape (Ellipse2D$Double. a b c d)]
    (doto graphics
      (.setColor (Color. red green blue alpha)))
    (when (:fill element)
      (.fill graphics shape))))


(def cached-paintings (atom {}))

(defn- paint-and-cache [transform elements]
  (let [image
        (double-buffer 1000 1000 transform
                       (fn [g]
                         (doseq [element elements]
                           (paint-element g element))))]
    (swap! cached-paintings assoc elements {:transform transform :image image})
    {:transform transform
     :image image}))

(defn paint-all [^Graphics2D g transform elements]
  (let [cached (get @cached-paintings elements)]
    (if (not (nil? cached))
      (let [old-transform (:transform cached)]
        (println old-transform transform)
        (doto g
          (.setTransform (swing.core/to-affine transform))
          (.drawImage (:image cached) 0 0 nil)))
      (doto g
        (.drawImage (:image (paint-and-cache transform elements)) 0 0 nil)))))