(ns galaxy-sim.vdom.tiles
  (:use [com.rpl.specter])
  (:require [swing.core])
  (:import (java.awt RenderingHints Graphics2D Color)))

(defn abs [n] (max n (- n)))

(def tile-width 1000)
(def tile-height 1000)

; paint with scale only, not translation
; fixed size tile, elements centered within

(defn- double-buffer [width height transform paint]
  (let [buffer (swing.core/create-compatible-image width height)
        graphics (.createGraphics buffer)]
    (doto graphics
      (swing.core/set-graphic-defaults)
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

(defn- in-range [element {:keys [x y width height]}]
  (and
    (>= (:x element) x)
    (<= (:x element) (+ x width))
    (>= (:y element) y)
    (<= (:y element) (+ y height))))

(defn- split-into-tiles [scale elements]
  (let [bounds (determine-bounds scale elements)
        x-parts (Math/ceil (/ (:width bounds) tile-width))
        y-parts (Math/ceil (/ (:height bounds) tile-height))
        min-x (:x bounds)
        min-y (:y bounds)]
    (for [x (range 0 x-parts)
          y (range 0 y-parts)]
      (let [tile-bounds {:x      (+ min-x (* x tile-width))
                         :y      (+ min-y (* y tile-height))
                         :width  tile-width
                         :height tile-height}
            tile-elements (->> elements
                               (filter #(in-range %1 tile-bounds))
                               (map #(assoc %1 :x (- (:x %1) (:x tile-bounds))
                                              :y (- (:y %1) (:y tile-bounds)))))]
        {:translate {:x (+ min-x (* tile-width x)) :y (+ min-y (* tile-height y))}
         :elements  tile-elements}))))

(defn render-tile [tile paint-element]
  (let [image (swing.core/create-compatible-image tile-width tile-height)
        graphics (.createGraphics image)]
    (swing.core/set-graphic-defaults graphics)
    (doseq [element (:elements tile)]
      (paint-element graphics element))
    (.dispose graphics)
    (assoc tile :image image)))

(def render-as-tiles
  (memoize
    (fn [scale elements paint-element]
      (for [tile (split-into-tiles scale elements)]
        (render-tile tile paint-element)))))