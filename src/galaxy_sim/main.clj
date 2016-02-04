(ns galaxy-sim.main
  (:require [galaxy-sim.astronomical.simulation :as simulation]
            [galaxy-sim.globals :as globals]
            [galaxy-sim.pan-zoom :as pan-zoom]
            [galaxy-sim.events :as events]
            [swing.frame]
            [galaxy-sim.vdom.painter :as painter]
            [galaxy-sim.vdom.tile-renderer :as tile-renderer])
  (:import (java.awt Graphics2D Color)
           (java.awt.image BufferedImage)
           (java.util.concurrent Executors ExecutorService)
           (com.sun.org.apache.xerces.internal.jaxp JAXPConstants)
           (javax.swing JPanel))
  (:gen-class))

(def drawables (atom []))

(def ^ExecutorService render-pool (Executors/newFixedThreadPool 16))

(defn- paint-sim [^Graphics2D g]
  (let [sim @globals/sim-state
        transform (:transform sim)
        scale (:scale transform)]
    (swing.core/set-graphic-defaults g)
    (doseq [d @drawables]
      (let [^BufferedImage image (:image d)
            transform (painter/create-transform scale (:translate transform) (:translate d))]
        (doto g
          (.setTransform transform)
          (.drawImage image 0 0 nil))))))

(defn- should-repaint [old-state new-state]
  (or (not= (:drawing new-state) (:drawing old-state))
      (not= (:transform new-state) (:transform old-state))
      (not= (:window new-state) (:window old-state))))

(defn start-game []
  (pan-zoom/init)
  (events/start-event-handler)
  (let [sim (simulation/create)
        drawing (simulation/draw sim)]
    (send globals/sim-state assoc :simulation sim :drawing drawing)
    (add-watch globals/sim-state :redraw
               (fn [key reference old-state new-state]
                 (when (or (not= (:simulation old-state) (:simulation new-state)))
                   (send reference #(assoc %1 :drawing (simulation/draw (:simulation %1)))))))
    (swing.core/invoke-later
      (let [frame (swing.frame/create "Galactic Simulation")
            ^JPanel view (swing.core/create-custom-component paint-sim)]
        (add-watch drawables :redraw (fn [k r old-state new-state]
                                       (println "REPAINT")
                                       (swing.core/invoke-later
                                         (.repaint view))))
        (add-watch globals/sim-state :canvas-repaint
                   (fn [k r old-state new-state]
                     (when (should-repaint old-state new-state)
                       (.submit render-pool
                                ^Callable (fn []
                                            (let [tiles (doall (tile-renderer/render-as-tiles
                                                                 (:transform new-state)
                                                                 (:window new-state)
                                                                 (:drawing new-state)))]
                                              (swap! drawables (fn [_] tiles))))))))
        (.add frame view)))))

(defn -main [& args]
  (start-game))
