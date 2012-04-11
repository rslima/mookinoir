(ns mookinoir.views.schedule
  (:use [noir.core :only [defpage]]
        mookinoir.views.layout))

(defpage "/schedule" {}
  (layout :schedule))
