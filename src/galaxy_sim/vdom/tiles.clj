(ns galaxy-sim.vdom.tiles
  (:use [com.rpl.specter])
  (:import (java.awt RenderingHints)))

(defn abs [n] (max n (- n)))

(def tile-width 1000)
(def tile-height 1000)

; paint with scale only, not translation
; fixed size tile, elements centered within

(defn- double-buffer [width height transform paint]
  (let [buffer (swing.core/create-compatible-image width height)
        graphics (.createGraphics buffer)]
    (doto graphics
      (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
      (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)
      (.setTransform (swing.core/to-affine transform))
      (paint)
      (.dispose))
    buffer))

(defn- determine-bounds [scale elements]
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


(defn- split-into-tiles [scale elements]
  (let [bounds (determine-bounds scale elements)
        x-parts (Math/ceil (/ (:width bounds) tile-width))
        y-parts (Math/ceil (/ (:height bounds) tile-height))]
    (for [x (range 0 x-parts)
          y (range 0 y-parts)]
      {:translate {:x x :y y}})))

(defn render-tile [tile]
  (let [image (swing.core/create-compatible-image tile-width tile-height)]
    (assoc tile :image image)))

(def render-as-tiles
  (memoize
    (fn [scale elements]
      (for [tile (split-into-tiles scale elements)]
        (render-tile tile)))))