(ns clgl.server
  (:require [noir.server :as server]
            noir.util.cljs))

(server/add-middleware noir.util.cljs/wrap-cljs)
(server/load-views "src/clgl/views/")


(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (def server (server/start port {:mode mode
                                    :ns 'clgl}))))
