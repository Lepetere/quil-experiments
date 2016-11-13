;; moving through a tunnel made of squares...

(ns quil-experiments.square-tunnel
  (:require [quil.core :as q]
            [quil.middleware :as m]))

  (def screen-width 1200)
  (def screen-height 720)
  (def frame-rate 50)

  (def z-speed 0.025) ; how many units to move forward per second on the z axis

  ;; define the base vertices of a cube
  (def v1 [-1 1 1])
  (def v2 [1 1 1])
  (def v3 [1 -1 1])
  (def v4 [-1 -1 1])
  (def v5 [-1 1 -1])
  (def v6 [1 1 -1])
  (def v7 [1 -1 -1])
  (def v8 [-1 -1 -1])

  (def square1 [v1 v2 v2 v6 v6 v5 v5 v1])
  (def square2 [v3 v4 v4 v8 v8 v7 v7 v3])
  (def square3 [v1 v4 v4 v8 v8 v5 v5 v1])
  (def square4 [v2 v6 v6 v7 v7 v3 v3 v2])

  (def squares [square1 square2 square3 square4])

  (defn draw [state]
    (q/background 0)
    (q/push-matrix)
    (q/translate (/ screen-width 2) (/ screen-height 2) +150)

    (q/scale 100)
    (q/begin-shape :lines)
    (doseq [translate-z-repeat (range 0 -60 -1.5)] ; repeat the drawing of the squares towards minus z
      (let [translate-z (+ (:z-progress state) translate-z-repeat)] ; z translation for the camera movement
        (doseq [square squares]
          (doseq [vertex square]
            (let [vertex-translated [(get vertex 0) (get vertex 1) (+ (get vertex 2) translate-z)]]
              (apply q/vertex vertex-translated))))))
    (q/end-shape)
    (q/pop-matrix))

(defn setup []
  (q/color-mode :hsb)
  (q/stroke 255)
  (q/fill 255)
  (q/stroke-weight 0.025)
  (q/frame-rate frame-rate)
  { :z-progress 6.0 })

(defn update-state [state]
  (if (<= (:z-progress state) 4.5)
    { :z-progress 6.0 }
    { :z-progress (- (:z-progress state) z-speed) }))

(q/defsketch quil-experiments
  :title "You spin my cube right round"
  :size [screen-width screen-height]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw
  ; use functional-mode middleware.
  :middleware [m/fun-mode]
  :renderer :opengl)
