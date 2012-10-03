(ns clgfx.shapes)

(defn hexagon [size]
  (let [row-size (* size 1.5)
        col-size (* size (Math/sqrt 3))
        half-row-size (/ row-size 2.0)]
    [[0 0], [size 0], [col-size half-row-size], [size row-size]
     [0 row-size], [(- size col-size), half-row-size], [0 0]]))
