;; a 3d cube that follows the mouse pointer in its orientation

(ns quil-experiments.follow-mouse-cube
  (:require [quil.core :as q]
            [quil.middleware :as m]))

  (def screen-width 1200)
  (def screen-height 720)

  (def xmag 0.0)
  (def ymag 0.0)

  (def pi 3.14159)

  (def vertices
    [[[133 255 255] [-1 1 1]]
    [[133 255 255] [1 1 1]]
    [[133 255 255] [1 -1 1]]
    [[133 255 255] [-1 -1 1]]
    [[43 255 255] [1 1 1]]
    [[43 255 255] [1 1 -1]]
    [[43 255 255] [1 -1 -1]]
    [[43 255 255] [1 -1 1]]
    [[177 255 255] [1 1 -1]]
    [[177 255 255] [-1 1 -1]]
    [[177 255 255] [-1 -1 -1]]
    [[177 255 255] [1 -1 -1]]
    [[219 255 255] [-1 1 -1]]
    [[219 255 255] [-1 1 1]]
    [[219 255 255] [-1 -1 1]]
    [[219 255 255] [-1 -1 -1]]
    [[91 255 255] [-1 1 -1]]
    [[91 255 255] [1 1 -1]]
    [[91 255 255] [1 1 1]]
    [[91 255 255] [-1 1 1]]
    [[102 255 255] [-1 -1 -1]]
    [[102 255 255] [1 -1 -1]]
    [[102 255 255] [1 -1 1]]
    [[102 255 255] [-1 -1 1]]])

  (defn draw [state]
    (q/background 0)
    (q/push-matrix)
    (q/translate (/ screen-width 2) (/ screen-height 2) -10)

    (let [new-xmag (* (/ (q/mouse-x) screen-width) pi)
          new-ymag (* (/ (q/mouse-y) screen-height) pi)
          xmag-diff (- xmag new-xmag)
          ymag-diff (- ymag new-ymag)]
      (if (> (Math/abs xmag-diff) 0.01)
        (q/rotate-y (- xmag (/ xmag-diff 2.0)))
        (q/rotate-y (- xmag)))
      (if (> (Math/abs ymag-diff) 0.01)
        (q/rotate-x (- ymag (/ ymag-diff 2.0)))
        (q/rotate-x (- ymag))))

    (q/scale 90)
    (q/begin-shape :quads)
    (doseq [[c v] vertices]
      (apply q/fill c)
      (apply q/vertex v))
    (q/end-shape)
    (q/pop-matrix))

(defn setup []
  (q/no-stroke)
  (q/color-mode :hsb)
  {})

(defn update-state [state]
  ;; state is not used at the moment
  {})

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
