(ns mookinoir.views.accounts
  (:use [noir.core :only [defpage]]
        mookinoir.views.layout))

(defpage "/accounts" {}
  (layout :accounts))
