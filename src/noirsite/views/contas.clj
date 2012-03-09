(ns noirsite.views.contas
  (:use [noir.core :only [defpage]]
        noirsite.views.layout))

(defpage "/contas" {}
  (layout :contas))
