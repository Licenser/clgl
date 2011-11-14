(ns clgfx.macros)

(defmacro transaction* [context & body]
  `(let [ctx# ~context]
     (.  ctx# ~(list 'save))
     ~@body
     (. ctx# ~(list 'restore))))

(defmacro transaction [context & body]
  `(doto ~context
     (. ~(list 'save))
     ~@body
     (. ~(list 'restore))))


(defmacro path* [context & body]
  `(let [ctx# ~context]
     (. ctx# ~(list 'beginPath))
     ~@body
     (. ctx# ~(list 'closePath))))

(defmacro path [context & body]
  `(doto ~context
     (. ~(list 'beginPath))
     ~@body
     (. ~(list 'closePath))))


(defmacro translate* [context x y & body]
  `(let [ctx# ~context]
     (transaction* ctx#
                   (.translate ctx# ~x ~y)
                   ~@body)))


(defmacro translate [context x y & body]
  `(transaction ~context
                (.translate ~x ~y)
                ~@body))

