(ns noirsite.views.root
  (:require [noirsite.views.common :as common]
            [noir.session :as session]
            [noir.response :as response])
  (:use [noir.core :only [defpage]]))

(defpage "/" []
  (if (session/get :user)
    (response/redirect "/inicio")
    (response/redirect "/welcome")))
