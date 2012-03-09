(ns noirsite.views.register
  (:use  [noir.core :only [defpage]]
         noirsite.views.layout))

(defpage "/register" {}
  (layout
    :register
    (-> "noirsite/views/register.html"
        select-container-div
        replace-container-div)))
