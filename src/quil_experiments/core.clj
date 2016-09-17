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
   :angle 0
   :circle-origins [] ; a vector of circle info maps {x: 0, y: 0, :color-offset 255, :angle-offset 0}
   })

(defn update-state [state]
  ; Update sketch state by changing circle color and position.
  (let [last-clicked-position (last (:circle-origins state))
        mouse-pressed (and (q/mouse-pressed?)
          (and (not= (q/mouse-x) (:x last-clicked-position)) (not= (q/mouse-y) (:y last-clicked-position))))]
    {:color-1 (mod (+ (:color-1 state) 0.7) 255)
   :color-2 (if (< (- (:color-2 state) 0.7) 0) 255 (- (:color-2 state) 0.7))
   :angle (+ (:angle state) 0.1)
   :circle-origins (if mouse-pressed
     (conj (:circle-origins state) {:x (q/mouse-x) :y (q/mouse-y) :color-offset (:color-1 state) :angle-offset (:angle state)})
     (:circle-origins state))}))

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 0)
  ;; print circles around previously clicked mouse positions
  (doseq [coordinates (:circle-origins state)]
    (let [angle (+ (:angle state) (:angle-offset coordinates))
          color-offset (:color-offset coordinates)
          color-1 (mod (+ (:color-1 state) color-offset) 255)
          color-2-temp (- (:color-2 state) color-offset)
          color-2 (if (< color-2-temp 0) (+ color-2-temp 255) color-2-temp)
          x-1 (* 150 (q/cos angle))
          y-1 (* 150 (q/sin angle))
          x-2 (* 100 (q/sin angle))
          y-2 (* 100 (q/cos angle))
          circle-origins (:circle-origins state)]
      (q/fill color-1 255 255)
      (q/ellipse (+ x-2 (:x coordinates)) (+ y-2 (:y coordinates)) 50 50)
      (q/fill color-2 255 255)
      (q/ellipse (+ x-1 (:x coordinates)) (+ y-1 (:y coordinates)) 100 100))))

(q/defsketch quil-experiments
  :title "You spin my circle right round"
  :size [1200 720]
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
