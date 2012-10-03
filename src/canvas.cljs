(ns clgfx.canvas
  (:require-macros [clgfx.macros :as cm]))

; Help texts are from http://dev.w3.org/html5/2dcontext/ since they map 1:1 to the context object

(defn begin-path [ctx]
  "Resets the current path."
  (.  ctx (beginPath)))

(defn move-to [ctx x y]
  "Creates a new subpath with the given point."
  (.moveTo ctx x y))

(defn close-path [ctx]
  "Marks the current subpath as closed, and starts a new subpath with a point the same as the start and end of the newly closed subpath."
  (.  ctx (beginPath)))

(defn line-to [ctx x y]
  "Adds the given point to the current subpath, connected to the previous one by a straight line."
  (.lineTo ctx x y))

(defn quadratic-curve-to [ctx cpx cpy x y]
  "Adds the given point to the current subpath, connected to the previous one by a quadratic Bézier curve with the given control point."
  (.quadraticCurveTo ctx cpx cpy x y))

(defn bezir-curve-to [ctx cp1x cp1y cp2x cp2y x y]
  "Adds the given point to the current subpath, connected to the previous one by a cubic Bézier curve with the given control points."
  (.bezierCurveTo ctx cp1x cp1y cp2x cp2y x y))

(defn arc-to [ctx x1 y1 x2 y2]
  "Adds an arc with the given control points and radius to the current subpath, connected to the previous point by a straight line.
Throws an INDEX_SIZE_ERR exception if the given radius is negative."
  (.arcTo ctx x1 y1 x2 y2))

(defn arc [ctx x y radius start-angle end-angle ? anticlockwise]
  "Adds points to the subpath such that the arc described by the circumference of the circle described by the arguments, starting at the given start angle and ending at the given end angle, going in the given direction (defaulting to clockwise), is added to the path, connected to the previous point by a straight line.
Throws an INDEX_SIZE_ERR exception if the given radius is negative."
  (.arc ctx x y radius start-angle end-angle (boolean anticlockwise)))

(defn rect [ctx x y w h]
  "Adds a new closed subpath to the path, representing the given rectangle."
  (.rect ctx x y w h))

(defn clip [ctx]
  "Further constrains the clipping region to the given path."
  (. ctx (clip)))

(defn is-point-in-path? [ctx x y]
  "Returns true if the given point is in the current path."
  (.isPointInPath ctx x y))

(defn stroke [ctx]
  "Strokes the subpaths with the current stroke style."
  (.  ctx (stroke)))

(defn fill [ctx]
  "Fills the subpaths with the current fill style."
  (.  ctx (fill)))

(defn scale [ctx x y]
  "Changes the transformation matrix to apply a scaling transformation with the given characteristics."
  (.scale ctx x y))

(defn rotate [ctx angle]
  "Changes the transformation matrix to apply a rotation transformation with the given characteristics. The angle is in radians."
  (.rotate ctx x y))

(defn translate [ctx x y]
  "Changes the transformation matrix to apply a translation transformation with the given characteristics."
  (.translate ctx x y))

(defn transform [ctx a b c d e f]
  "Changes the transformation matrix to apply the matrix given by the arguments as described below."
  (.transform ctx a b c d e f))

(defn set-transform [ctx a b c d e f]
  "Changes the transformation matrix to the matrix given by the arguments as described below."
  (.setTransform ctx a b c d e f))

(defn add-color-stop [gradient offset color]
  "Adds a color stop with the given color to the gradient at the given offset. 0.0 is the offset at one end of the gradient, 1.0 is the offset at the other end.
Throws an INDEX_SIZE_ERR exception if the offset is out of range. Throws a SYNTAX_ERR exception if the color cannot be parsed."
   (.addColorStop gradient offset color))

(defn create-linear-gradient [ctx x0 y0 x1 y1]
  "Returns a CanvasGradient object that represents a linear gradient that paints along the line given by the coordinates represented by the arguments.
If any of the arguments are not finite numbers, throws a NOT_SUPPORTED_ERR exception."
  (.createLinearGradient ctx x0 y0 x1 y1))

(defn create-radial-gradient [ctx x0 y0 r0 x1 y1 r1]
  "Returns a CanvasGradient object that represents a radial gradient that paints along the cone given by the circles represented by the arguments.
If any of the arguments are not finite numbers, throws a NOT_SUPPORTED_ERR exception. If either of the radii are negative, throws an INDEX_SIZE_ERR exception."  
  (.createRadialGradient ctx x0 y0 r0 x1 y1 r1))

(defn create-pattern [ctx image repetition]
  "Returns a CanvasPattern object that uses the given image and repeats in the direction(s) given by the repetition argument.
The allowed values for repetition are repeat (both directions), repeat-x (horizontal only), repeat-y (vertical only), and no-repeat (neither). If the repetition argument is empty or null, the value repeat is used.
If the first argument isn't an img, canvas, or video element, throws a TYPE_MISMATCH_ERR exception. If the image has no image data, throws an INVALID_STATE_ERR exception. If the second argument isn't one of the allowed values, throws a SYNTAX_ERR exception. If the image isn't yet fully decoded, then the method returns null."
  (.createPattern ctx image, repetition))

(defn clear-rect [ctx x y w h]
  "Clears all pixels on the canvas in the given rectangle to transparent black."
  (.clearRectx ctx x y w h)) 

(defn fill-rect [ctx x y w h]
  "Paints the given rectangle onto the canvas, using the current fill style."
  (.fillRect ctx x y w h)) 

(defn stroke-rect [ctx x y w h]
  "Paints the box that outlines the given rectangle onto the canvas, using the current stroke style."
  (.strokeRect ctx x y w h)) 

(defn draw-focus-ring [ctx element ? can-draw-custom]
  "If the given element is focused or a descendant of the element with focus, draws a focus ring around the current path, following the platform conventions for focus rings.
If the canDrawCustom argument is true, then the focus ring is only drawn if the user has configured his system to draw focus rings in a particular manner. (For example, high contrast focus rings.)
Returns true if the given element is focused, the canDrawCustom argument is true, and the user has not configured his system to draw focus rings in a particular manner. Otherwise, returns false.
When the method returns true, the author is expected to manually draw a focus ring."
  (.drawFocusRing ctx element (boolean can-draw-custom)))


(defn set-caret-selection-rect [ctx element x y w h]
  "Returns true if the given element is focused or a document descendant of an element with focus. Otherwise, returns false."
  (.setCaretSelectionRect ctx element, x, y, w, h))

(defn caret-blink-rate [ctx]
  "Returns the blink rate of the system in milliseconds if supported. Otherwise, returns -1 if it is unsupported by the system."
  (. ctx (caretBlinkRate)))

(defn fill-text 
  "Fills the given text at the given position. If a maximum width is provided, the text will be scaled to fit that width if necessary."
  ([ctx text x y]
     (.fillText ctx text x y))
  ([ctx text x y max-width]
     (.fillText ctx text x y maxWidth)))

(defn stroke-text 
  "Strokes the given text at the given position. If a maximum width is provided, the text will be scaled to fit that width if necessary."
  ([ctx text x y]
     (.strokeText ctx text x y))
  ([ctx text x y max-width]
     (.strokeText ctx text x y maxWidth)))

(defn measure-text [ctx text]
  "Returns a TextMetrics object with the metrics of the given text in the current font."
  (.measureText ctx text))

(defn text-width [ctx text]
  "Returns the advance width of the text in the current font."
  (.width (.measureText ctx text)))

(defn draw-image
  "Draws the given image onto the canvas.
s stands for sorce
d stands for destination
x,y,w,h stand for coordinates and size respectively.
If the first argument isn't an img, canvas, or video element, throws a TYPE_MISMATCH_ERR exception. If the image has no image data, throws an INVALID_STATE_ERR exception. If the one of the source rectangle dimensions is zero, throws an INDEX_SIZE_ERR exception. If the image isn't yet fully decoded, then nothing is drawn."
  ([ctx image dx dy]
     (.drawImage ctx image dx dy))
  ([ctx image dx dy dw dh]
     (.drawImage ctx image dx dy dw dh))
  ([ctx image sx sy sw sh dx dy dw dh]
     (.drawImage ctx image sx sy sw sh dx dy dw dh)))




(defn get-props [ctx]
 {:width               (.lineWidth ctx)
  :style               (.strokeStyle ctx)
  :cap                 (.lineCap ctx)
  :filling             (.fillStyle ctx)
  :shadow-color        (.shadowColor ctx)
  :shadow-blur         (.shadowBlur ctx)
  :shadow-x            (.shadowOffsetX ctx)
  :shadow-y            (.shadowOffsetY ctx)
  :global-alpha        (.globalAlpha ctx)
  :composite-operation (.globalCompositeOperation ctx)
  :line-join           (.lineJoin ctx)
  :miter-limit         (.miterLimit ctx)
  :font                (.font ctx)
  :text-align          (.textAlign ctx)
  :text-baseline       (.textBaseline ctx)})

(defn set-prop [ctx prop val]
  (if val
    (condp = prop
      :width               (set! (.lineWidth ctx) val)
      :style               (set! (.strokeStyle ctx) val)
      :cap                 (set! (.lineCap ctx) val)
      :filling             (set! (.fillStyle ctx) val)
      :shadow-color        (set! (.shadowColor ctx) val)
      :shadow-blur         (set! (.shadowBlur ctx) val)
      :shadow-x            (set! (.shadowOffsetX ctx) val)
      :shadow-y            (set! (.shadowOffsetY ctx) val)
      :global-alpha        (set! (.globalAlpha ctx) val)
      :composite-operation (set! (.globalCompositeOperation ctx) val)
      :line-join           (set! (.lineJoin ctx) val)
      :miter-limit         (set! (.miterLimit ctx) val)
      :font                (set! (.font ctx) val)
      :text-align          (set! (.textAlign ctx) val)
      :text-baseline       (set! (.textBaseline ctx) val))))

(defn set-props [ctx props]
  (doseq [[p v] props]
    (set-prop ctx p v)))

(defn draw-path [ctx [[x y] & points]]
  (move-to ctx x y)
  (doseq [[x y] points]
    (line-to ctx x y)))

(defn line
  ([ctx style points]
     (cm/transaction ctx
                     (set-props style)
                     (draw-path points)
                     (stroke)))
  ([ctx points]
     (line ctx {} points)))

(defn shape
  ([context style points]
     (cm/transaction
      context
      (cm/path
       (line style points)
       (fn [c] (if (:filled style) (fill c))))
      (stoke)))
  ([ctx points]
     (shape {} points)))