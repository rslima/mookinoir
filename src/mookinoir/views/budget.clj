(ns mookinoir.views.budget
  (:use [noir.core :only [defpage]]
        mookinoir.views.layout))

(defpage "/budget" {}
  (layout :budget))
