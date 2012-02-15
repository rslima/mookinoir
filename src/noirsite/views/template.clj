(ns noirsite.views.template
  (:use [net.cgrand.enlive-html :only [defsnippet substitute]]
        [noirsite.views.common :only [layout render-snippet]]
        [noir.core :only [defpage]]))

(defsnippet hello-enlive
  "noirsite/views/template.html" [:div.container]
  [name]
  [:#nome] (substitute name))

(defn- layout-template [nodes]
  (layout :template nodes))

(defpage "/template" []
  (-> "Ricardo" hello-enlive render-snippet layout-template))
