(ns quil-experiments.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:color-1 0
   :color-2 255
   :angle 0})

(defn update-state [state]
  ; Update sketch state by changing circle color and position.
  {:color-1 (mod (+ (:color-1 state) 0.7) 255)
   :color-2 (if (< (- (:color-2 state) 0.7) 0) 255 (- (:color-2 state) 0.7))
   :angle (+ (:angle state) 0.1)})

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 0)
  ; Calculate x and y coordinates of the circle.
  (let [angle (:angle state)
        x-1 (* 150 (q/cos angle))
        y-1 (* 150 (q/sin angle))
        x-2 (* 100 (q/sin angle))
        y-2 (* 100 (q/cos angle))]
    ; Move origin point to the center of the sketch.
    (q/with-translation [(q/mouse-x) (q/mouse-y)]
      (q/fill (:color-1 state) 255 255)
      (q/ellipse x-2 y-2 50 50)
      (q/fill (:color-2 state) 255 255)
      (q/ellipse x-1 y-1 100 100))))

(q/defsketch quil-experiments
  :title "You spin my circle right round"
  :size [700 700]
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
