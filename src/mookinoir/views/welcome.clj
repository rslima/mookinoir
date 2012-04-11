(ns mookinoir.views.welcome
  (:use [noir.core :only [defpage]]
        mookinoir.views.layout))

(defpage "/welcome" {}
  (layout :welcome (-> "mookinoir/views/welcome.html" select-container-div replace-container-div)))
