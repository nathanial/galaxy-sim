(ns galaxy-sim.vdom.painter
  (:import [java.awt Color Graphics2D RenderingHints GraphicsEnvironment Transparency]
           [java.awt.geom Ellipse2D$Double])
  (:use [com.rpl.specter]))

(defn abs [n] (max n (- n)))

(def cached-paintings (atom {}))

(def image-width 1000)
(def image-height 1000)


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

(defn- determine-bounds [elements]
  (let [x-coords (select [ALL :x] elements)
        y-coords (select [ALL :y] elements)
        min-x (apply min x-coords)
        max-x (apply max x-coords)
        min-y (apply min y-coords)
        max-y (apply max y-coords)]
    {
     :x min-x,
     :y min-y,
     :width (abs (- min-x max-x)),
     :height (abs (- min-y max-y))
     }))

(defn- split-into-tiles [bounds elements]
  (println "X Partitions" (Math/ceil (/ (:width bounds) image-width)))
  (println "Y Partitions" (Math/ceil (/ (:height bounds) image-height))))

(defmulti paint-element (fn [_ element] (:type element)))

(defmethod paint-element :circle [^Graphics2D graphics element]
  (let [[^Integer red ^Integer green ^Integer blue alpha?] (:color element)
        ^Integer alpha (if (nil? alpha?) 255 alpha?)
        x (:x element)
        y (:y element)
        shape (Ellipse2D$Double. x y (* 2 (:radius element)) (* 2 (:radius element)))]
    (doto graphics
      (.setColor (Color. red green blue alpha))
      (.draw shape))
    (when (:fill element)
      (.fill graphics shape))))

(defn- paint-and-cache [transform elements]
  (let [bounds (determine-bounds elements)
        tiles (split-into-tiles bounds elements)]
    (println "Tiles" tiles)
    (let [image
          (double-buffer image-width image-height transform
                         (fn [g]
                           (doseq [element elements]
                             (paint-element g element))))]
      (swap! cached-paintings assoc elements {:transform transform :image image})
      {:transform transform
       :image     image})))


(defn paint-all [^Graphics2D g transform elements]
  (let [cached (get @cached-paintings elements)]
    (if (not (nil? cached))
      (let [old-transform (:transform cached)
            new-transform {:translate {:x (- (get-in transform [:translate :x])
                                             (get-in old-transform [:translate :x]))
                                       :y (- (get-in transform [:translate :y])
                                             (get-in old-transform [:translate :y]))}
                           :scale     {:x 1.0 :y 1.0}}]
        (doto g
          (.setTransform (swing.core/to-affine new-transform))
          (.drawImage (:image cached) 0 0 nil)))
      (doto g
        (.drawImage (:image (paint-and-cache transform elements)) 0 0 nil)))))