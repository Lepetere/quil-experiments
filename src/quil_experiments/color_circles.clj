;; Clicking on the canvas adds two colored circles revolving around the point clicked
;; in opposite directions.

(ns quil-experiments.color-circles
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def x-width 1200)
(def y-height 720)
(def margin 50)

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :rgb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {})

(defn update-state [state]
  {})

(defn draw-state [state]
  ;; reset background
  (q/background 0 0 0)
  ;; draw circles
  (q/no-fill)
  (doseq [x-coord (range (- 0 margin) (+ x-width margin) 50)
          y-coord (range (- 0 margin) (+ y-height margin) 50)
          color (range 0 255 1)]
    (q/with-stroke [color (- 255 color) color]
      (q/ellipse x-coord y-coord 100 100))))

(q/defsketch quil-experiments
  :title "You push my circle right round"
  :size [x-width y-height]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
