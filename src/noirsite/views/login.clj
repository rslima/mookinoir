(ns noirsite.views.login
  (:use [noir.core :only [defpage]]
        noirsite.views.layout))

(defpage "/login" {}
  (layout 
    :login
    (-> "noirsite/views/login.html"
        select-container-div
        replace-container-div)))
