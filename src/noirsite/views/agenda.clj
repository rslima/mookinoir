(ns noirsite.views.agenda
  (:use [noir.core :only [defpage]]
        noirsite.views.layout))

(defpage "/agenda" {}
  (layout :agenda))
