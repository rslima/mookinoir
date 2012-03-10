(ns noirsite.views.login
  (:require [noir.response :as resp]
            [noir.session :as session]
            [noirsite.models.user :as user])
  (:use [noir.core :only [defpage render]]
        noirsite.views.layout))

(defpage "/login" {}
  (if (session/get :user)
    (resp/redirect "/inicio")
    (layout
      :login
      (-> "noirsite/views/login.html"
          select-container-div
          replace-container-div))))

(defpage [:post "/login"]
  {:keys [email password]
   :or {email "" password ""}
   :as form}
  (when-not (session/get :user)
    (let
      [user (user/authenticate form)]
      (if user
        (do
          (session/put! :user user)
          (resp/redirect "/inicio"))
        (render "/register" {})))))

(defpage "/logout"
  {}
  (session/remove! :user)
  (resp/redirect "/welcome"))