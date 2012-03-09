(ns noirsite.views.welcome
  (:use [noir.core :only [defpage]]
        noirsite.views.layout))

(defpage "/welcome" {}
  (layout :welcome (-> "noirsite/views/welcome.html" select-container-div replace-container-div)))
