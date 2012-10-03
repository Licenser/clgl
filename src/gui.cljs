(ns ds.gui)

(def window (js* "window"))
(def document (js* "document"))
(def draw-engine (js* "DrawEngine"))

(def window-width (.innerWidth window))
(def window-heigt (.innerHeight window))
(def offset (atom {:x 0
                    :y 0
                    :start {:x 0
                            :y 0}
                    :start {:x 0
                            :y 0}}))

(def hexagon-size 20)
(def redraw-delay 50)
(def frames-per-tick 40)
(def min-hexagon-size 3)
(def max-hexagon-size 200)
(def stopped false)
(def delux true)
(def )

(defn start-at [x y]
  (swap! offset assoc :start {:x x 
                              :y y})
  (swap! offset assoc :old {:x (:x @offset)
                            :y (:y @offset)}))

(defn move-to [x y]
  (swap! offset assoc
         :x
         (+ (get-in @offset [:old :x]) (- x (get-in @offset [:start :x])))
         :y
         (+ (get-in @offset [:old :y]) (- y (get-in @offset [:start :y])))))



(defn initialize [name]
  (.setTitle document " - Starting...")
  (.initialize draw-engine)
  (setup-fighter-canvas)
  (setup-fire-canvas)
  (setup-unit-canvas)
  (setup-grid-canvas)
  (setup-selection-canvas)
  (setup-osd-canvas)
  (setup-image-canvas)
  (setup-hexagon)
  (swap! offset assoc
         :x (- (Math/round (/ window-width 2)) (/ col-width 3) )
         :y (- (Math/round (/ window-heigt 2)) (/ row-width 2) ))
  (init-map)
  (init-battle)
  (load-battle))


(defn setup-fighter-canvas []
  )
(defn setup-fire-canvas []
  )
(defn setup-unit-canvas []
  )
(defn setup-grid-canvas []
  )
(defn setup-selection-canvas []
  )
(defn setup-osd-canvas []
  )
(defn setup-image-canvas []
  )
(defn setup-hexagon []
  )
(defn init-map []
  )
(defn init-battle []
  )
(defn load-battle []
  )
