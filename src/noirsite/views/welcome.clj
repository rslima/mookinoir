(ns noirsite.views.welcome
  (:use [noir.core :only [defpage]]
        noirsite.views.layout
        [net.cgrand.enlive-html :only [defsnippet]]))

(defpage "/welcome" []
  (layout :welcome (-> "noirsite/views/welcome.html" select-container-div replace-container-div)))
