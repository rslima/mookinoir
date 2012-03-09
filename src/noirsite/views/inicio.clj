(ns noirsite.views.inicio
  (:use [noir.core :only [defpage]]
        noirsite.views.layout))

(defpage "/inicio" {}
  (layout :inicio))