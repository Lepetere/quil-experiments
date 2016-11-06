;; two rotating squares

(ns quil-experiments.moving-grid
  (:require [quil.core :as q]
            [quil.middleware :as m]))

  (def screen-width 1200)
  (def screen-height 720)
  (def frame-rate 50)
  (def pi 3.14159)

  (def rotation-speed 0.15) ;; how many turns per second

  (defn draw [state]
    (q/background 0)
    (q/push-matrix)
    (q/translate (/ screen-width 2) (/ screen-height 2) +150)

    (q/rotate-y (q/radians (:rotation-degree state)))

    (q/scale 100)
    (q/begin-shape :lines)
    (doseq [y (range -13 13 2)
            z (range -13 13 2)]
      ;; horizontal 'forward' lines
      (q/stroke-weight 0.02)
      (apply q/vertex [-50 (* y 2.5) (* z 2.5)])
      (apply q/vertex [50 (* y 2.5) (* z 2.5)])
      ;; horizontal 'cross' lines
      (q/stroke-weight 0.01)
      (apply q/vertex [(* y 2.5) -50 (* z 2.5)])
      (apply q/vertex [(* y 2.5) 50 (* z 2.5)])
      ;; vertical lines
      (q/stroke-weight 0.015)
      (apply q/vertex [(* y 2.5) (* z 2.5) -50])
      (apply q/vertex [(* y 2.5) (* z 2.5) 50]))
    (q/end-shape)
    (q/pop-matrix))

(defn setup []
  (q/color-mode :hsb)
  (q/no-fill)
  (q/stroke 255)
  (q/stroke-weight 0.01)
  (q/frame-rate frame-rate)
  { :rotation-degree 0.0 })

(defn update-state [state]
  { :rotation-degree (mod (+ (/ 360 (/ frame-rate rotation-speed)) (:rotation-degree state)) 360) })

(q/defsketch quil-experiments
  :title "You spin my grid right round"
  :size [screen-width screen-height]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw
  ; use functional-mode middleware.
  :middleware [m/fun-mode]
  :renderer :opengl)
