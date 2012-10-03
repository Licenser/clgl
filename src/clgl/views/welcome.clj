(ns clgl.views.welcome
  (:require [clgl.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/welcome" []
  (common/layout
   [:canvas {:id "fighter"} "This demo needs HTML 5 Canvas."]
   [:canvas {:id "fire"} "---"]
   [:canvas {:id "units"} "Please install a better browser."]
   [:canvas {:id "grid" :width 250 :height 250} "---"]
   [:canvas {:id "selection"} "Firefox or Safari, for example."]
   [:canvas {:id "osd" :style "display: block"} "Or both."]))