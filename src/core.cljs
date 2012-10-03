(ns clgfx.core
  (:require [clgfx.canvas :as canvas]
            [clgfx.webgl :as webgl]
            [clgfx.shapes :as shapes])
  (:require-macros [clgfx.macros :as cm]))

(def window (js* "window"))
(def alert (js* "alert"))

(defn get-element [e]
  (.getElementById (.document window) e))


(defn get-context [canvas type]
  (.getContext (get-element canvas) type))

(defn hexagon-grid [ctx size x1 y1 x2 y2]
  (let [row-size (* size 1.5)
        col-size (* size (Math/sqrt 3))
        half-row-size (/ row-size 2.0)
        y-offset 100
        y-offset2 (+ y-offset half-row-size)
        x-offset 100
        hexagon (shapes/hexagon size)]

    (doseq [x (range (min x1 x2) (max x1 x2))
            y (range (min y1 y2) (max y1 y2))]
      (cm/translate ctx
                    (+ (* x col-size) x-offset)
                    (+ (* y row-size) (if (odd? x) y-offset y-offset2))
                    (canvas/line {:style "green"} hexagon)))))



(defn ^:export grid []
  (doto (get-context "grid" "2d")
    (hexagon-grid 20 2 2 -2 -2)))