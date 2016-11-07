;; interlaced rotating pyramids

(ns quil-experiments.rotating-pyramids
  (:require [quil.core :as q]
            [quil.middleware :as m]))

  ;; sketch constants
  (def screen-width 1200)
  (def screen-height 720)
  (def frame-rate 50)
  (def rotation-speed 0.125) ;; how many turns per second
  (def pi 3.14159)

  ;; base triangle vertices
  (def v1 [-0.5 0.288675 -0.288675])
  (def v2 [0.5 0.288675 -0.288675])
  (def v3 [0 0.288675 0.711325])
  ;; top vertex
  (def vt [0 -0.711325 0])

  ;; this represents one pyramid
  (def vertices
    [v1 ;; bottom triangle
    v2
    v2
    v3
    v3
    v1
    v1 ;; upward line 1
    vt
    v2 ;; upward line 2
    vt
    v3 ;; upward line 3
    vt])

  (defn draw [state]
    (q/background 0 0 0)
    (q/push-matrix)
    (q/translate (/ screen-width 2) (/ screen-height 2) +150)

    (q/rotate-y (q/radians (:rotation-degree state)))

    (q/scale 100)
    (q/begin-shape :lines)
    (doseq [scale (range 0 150 1.5)] ; draw several pyramids and scale them
      (doseq [vertex vertices]
        (let [vertex-scaled (map #(* scale %) vertex)]
          (apply q/vertex vertex-scaled))))
    (q/end-shape)
    (q/pop-matrix))

(defn setup []
  (q/color-mode :rgb)
  (q/no-fill)
  (q/stroke 11 133 0)
  (q/stroke-weight 0.05)
  (q/frame-rate frame-rate)
  { :rotation-degree 0.0 })

(defn update-state [state]
  { :rotation-degree (mod (+ (/ 360 (/ frame-rate rotation-speed)) (:rotation-degree state)) 360) })

(q/defsketch quil-experiments
  :title "You spin my pyramids right round"
  :size [screen-width screen-height]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw
  ; use functional-mode middleware.
  :middleware [m/fun-mode]
  :renderer :opengl)
