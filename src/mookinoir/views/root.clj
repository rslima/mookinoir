(ns mookinoir.views.root
  (:require [noir.session :as session]
            [noir.response :as response])
  (:use [noir.core :only [defpage]]))

(defpage "/" {}
  (if (session/get :user)
    (response/redirect "/home")
    (response/redirect "/welcome")))
