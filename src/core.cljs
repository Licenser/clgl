(ns clgfx.core
  (:require [clgfx.canvas :as canvas])
  (:require-macros [clgfx.macros :as cm]))


(def window (js* "window"))
(def alert (js* "alert"))
(def Float32Array (js* "Float32Array")) 

(def *gl*)
(def *prog*)

(defn get-element [e]
  (.getElementById (.document window) e))


(defn ^:fa float-array [s]
  (Float32Array. (to-array s)))

(defn attach-shader
  ([gl prog type src]
     (binding [*gl* gl 
               *prog* prog]
       (attach-shader type src)))
  ([type src]
       (let [shader (.createShader *gl* type)]
       (.shaderSource *gl* shader src)
       (.compileShader *gl* shader)
       (.attachShader *gl* *prog* shader))))

(defn get-context [canvas type]
  (.getContext (get-element canvas) type))

(defn new-3dcontext [canvas v-shader-source f-shader-source]
  (let [gl (get-context canvas "experimental-webgl")
        prog (. gl (createProgram))]
    (doto gl
      (attach-shader prog (.VERTEX_SHADER gl) v-shader-source)
      (attach-shader prog (.FRAGMENT_SHADER gl) f-shader-source)
      (.linkProgram prog)
      (.useProgram prog)
      (.clearColor 0.0, 0.0, 0.0, 1.0) 
      (.clear (.COLOR_BUFFER_BIT gl)))
    [gl prog])) 

(defn ^:export ex1 []
  (let [[gl webGLProgramObject]
        (new-3dcontext
         "canvas"
         "attribute vec4 vPosition;
          void main() {
            gl_Position = vPosition;
          }"
         "precision mediump float;
          void main() {
            gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
          }")
        vVertices (float-array [0.0  0.1 0.0
                                -0.1 -0.1 0.0
                                0.1 -0.1 0.0
                                ])
        vVertices2 (float-array [0.0  0.1 0.0
                                 -0.1 -0.1 0.0
                                 0.1 -0.1 0.0
                                 0.0  0.1 0.0])
        vertexAttribLoc (.getAttribLocation gl webGLProgramObject "vPosition")
        vertexPosBufferObjekt (. gl (createBuffer))]
    (.bindBuffer gl (.ARRAY_BUFFER gl) vertexPosBufferObjekt)
    (.bufferData gl (.ARRAY_BUFFER gl) vVertices2 (.STATIC_DRAW gl))
    (.vertexAttribPointer gl vertexAttribLoc 3 (.FLOAT gl) false 0 0)
    (.enableVertexAttribArray gl vertexAttribLoc)
    (.drawArrays gl (.LINE_STRIP gl) 0 4)))

(defn ^:export c1 []
  (cm/translate (get-context "canvas" "2d") 100 50 
               (canvas/line {:points [[0 100] [350 0] [350 100] [0 100]]})))

(defn ^:export c2 []
  (let [ctx (get-context "canvas" "2d")]
    (doseq [x (range -20 20)
            y (range -20 20)]
      (canvas/hexagon ctx x y)))) 