(ns clgfx.canvas
  (:require-macros [clgfx.macros :as cm]))

(defn draw-line [ctx [[x y] & points]]
  (.moveTo ctx x y)
  (doseq [[x y] points]
    (.lineTo ctx x y)))

(defn begin-path [ctx]
  (.  ctx (beginPath)))

(defn close-path [ctx]
  (.  ctx (beginPath)))

(defn stroke [ctx]
  (.  ctx (stroke)))

(defn fill [ctx]
  (.  ctx (fill)))

(defn set-prop [ctx prop val]
  (if val
    (condp = prop
      :width   (set! (.lineWidth ctx) val)
      :stroke  (set! (.strokeStyle ctx) val)
      :cap     (set! (.lineCap ctx) val)
      :filling (set! (.fillStyle ctx) val))))

(defn set-props [ctx props]
  (doseq [[p v] props]
    (set-prop ctx p v)))

(defn line
  ([ctx style points]
     (cm/transaction ctx
                     (set-props style)
                     (draw-line points)
                     (stroke)))
  ([ctx points]
     (line ctx {} points)))

(defn shape
  ([context style points]
     (cm/transaction
      context
      (cm/path
       (line style points)
       (fn [c] (if (:filled style) (fill c))))))
  ([ctx points]
     (shape {} points)))

(defn hexagon
  [ctx x y & {style :style
              size :size
              x-offset :x-offset, y-offset :y-offset
              :or {size 20, x-offset 0, y-offset 0}}]
  (let [row-size (* size 1.5)
        col-size (* size (Math/sqrt 3))
        half-row-size (/ row-size 2.0)
        points [[0 0], [size 0], [col-size half-row-size], [size row-size]
                [0 row-size], [(- size col-size), half-row-size], [0 0]]]
    (cm/translate ctx (+ (* x col-size) x-offset) (+ (* y row-size) y-offset (if (even? x) half-row-size 0))
                  (shape style points))))

 