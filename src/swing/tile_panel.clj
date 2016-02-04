(ns swing.tile-panel
  (:require [swing.core])
  (:import (java.awt Graphics2D)))

(defn- paint-tile [^Graphics2D g tile]
  (println "Paint Tile" tile))

(defn- paint-tiles [^Graphics2D g tiles]
  (doseq [tile tiles]
    (paint-tile g tile)))

(defn create [tiles]
  (let [tile-panel (swing.core/create-custom-component #(paint-tiles %1 tiles))
        redraw-listener (fn [key ref old new]
                          (swing.core/invoke-later
                            (.repaint tile-panel)))]
    (add-watch tiles :redraw redraw-listener)
    tile-panel))
