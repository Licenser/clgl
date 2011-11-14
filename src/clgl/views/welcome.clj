(ns clgl.views.welcome
  (:require [clgl.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/welcome" []
  (common/layout
   [:canvas {:id "canvas" :width "500" :height "500"}]))