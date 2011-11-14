(ns clgl.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css include-js html5]]))

(defpartial layout [& content]
            (html5
             [:head
              [:title "clgl"]
              (include-css "/css/reset.css")
              (include-js "/cljs/bootstrap.js")]
              [:body
               [:div#wrapper
                content]]))
