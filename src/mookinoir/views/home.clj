(ns mookinoir.views.home
  (:use [noir.core :only [defpage]]
        [mookinoir.views.layout :only [layout]]))

(defpage "/home" {}
  (layout :home))
