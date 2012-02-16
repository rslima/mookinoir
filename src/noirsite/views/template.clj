(ns noirsite.views.template
  (:use [net.cgrand.enlive-html :only [defsnippet substitute]]
        noirsite.views.layout
        [noir.core :only [defpage]]))

(defsnippet hello-enlive
  "noirsite/views/template.html" [:div.container]
  [name]
  [:#nome] (substitute name))

(defpage "/template" []
  (layout :template (-> "Ricardo" hello-enlive replace-container-div)))
