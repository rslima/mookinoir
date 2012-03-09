(ns noirsite.views.orcamento
  (:use [noir.core :only [defpage]]
        noirsite.views.layout))

(defpage "/orcamento" {}
  (layout :orcamento))
