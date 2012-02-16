(ns noirsite.views.welcome
  (:use [noir.core :only [defpage]]
        noirsite.views.layout
        [net.cgrand.enlive-html :only [defsnippet]]))

(defsnippet welcome-page
  "noirsite/views/welcome.html" [:div.container]
  []
  [:div.container] identity)

(defpage "/welcome" []
  (layout :welcome (-> (welcome-page) replace-container-div)))
