(ns clgfx.webgl)

(def Float32Array (js* "Float32Array"))

(defn float-array [s]
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


(defn to-3d [{gl :gl} [x y]]
  (let [w (.width (.canvas gl))
        h (.height (.canvas gl))
        px (* (/ x w) 2)
        py (* (/ y h) 2)]
    [(- px 1) (- 1 py) 0]))

(defn mat4identity []
  [1 0 0 0
   0 1 0 0
   0 0 1 0
   0 0 0 1])

(defn mat4translate [ctx
                     [a b c d
                      e f g h
                      i j k l
                      m n o p]
                     trans]
  (let [[x y z] (to-3d ctx trans)]
    [a b c d
     e f g h
     i j k l
     (+ (* a x) (* e y) (* i z) m)
     (+ (* b x) (* f y) (* j z) n)
     (+ (* c x) (* g y) (* k z) o)
     (+ (* d x) (* h y) (* l z) p)]))


(defn set-matrix [gl attr matrix]
  (.uniformMatrix4fv gl attr false (float-array matrix)))

(defn init-context [gl ]
  (let [v-shader-source "attribute vec4 aVertexPosition;
    uniform mat4 uMVMatrix;
    uniform mat4 uPMatrix;

    void main(void) {
        gl_Position =  uMVMatrix * uPMatrix * aVertexPosition;
    }"
        f-shader-source "precision mediump float;
          void main() {
            gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
          }"
        prog (. gl (createProgram))]
    (doto gl
      (attach-shader prog (.FRAGMENT_SHADER gl) f-shader-source)
      (attach-shader prog (.VERTEX_SHADER gl) v-shader-source)
      (.linkProgram prog)
      (.useProgram prog)
      (.clearColor 0.0, 0.0, 0.0, 1.0) 
      (.clear (.COLOR_BUFFER_BIT gl)))
    (let [pos (.getAttribLocation gl prog "aVertexPosition")
          p-matrix (.getUniformLocation gl prog, "uPMatrix")
          mv-matrix (.getUniformLocation gl prog, "uMVMatrix")]
      (set-matrix gl mv-matrix (mat4identity))
      (set-matrix gl p-matrix (mat4identity))
      (.enableVertexAttribArray gl pos)
;      (.viewport gl 0 0 (.viewportWidth gl) (.viewportHeight gl)) ;
      {:gl gl
       :pos pos
       :p-matrix p-matrix
       :mv-matrix mv-matrix
       :prog prog})))


(defn buffer [{gl :gl
               pos :pos
               prog :prog :as ctx} points]
  (let [buffer (. gl (createBuffer))]
    (.bindBuffer gl (.ARRAY_BUFFER gl) buffer)
    (.bufferData gl
                 (.ARRAY_BUFFER gl)
                 (float-array points)
                 (.STATIC_DRAW gl))
    (set! (.itemSize buffer) 3)
    (set! (.numItems buffer) (/ (count points) 3))
    (set! (.type buffer) (.LINE_STRIP gl))
    buffer))

(defn line [ctx points]
  (buffer ctx (flatten (map #(to-3d ctx %) points))))

(defn translate [{gl :gl
                  mv-matrix :mv-matrix
                  :as ctx} x y]
  (let [trans (mat4translate ctx (mat4identity) [x y])]
    (set-matrix gl mv-matrix trans)))



(defn draw [{gl :gl
             pos :pos
             p-matrox :p-matrix
             mv-matrix :mv-matrix
             prog :prog :as ctx} & buffers]
  (doseq [b buffers]
    (.bindBuffer gl (.ARRAY_BUFFER gl) b)
    (.vertexAttribPointer gl
                          pos,
                          (.itemSize b), (.FLOAT gl), false, 0, 0)
    (translate ctx 0 0)
    (.drawArrays gl (.type b) 0 (.numItems b))))


