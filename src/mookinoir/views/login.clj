(ns mookinoir.views.login
  (:require [noir.response :as resp]
            [noir.session :as session]
            [mookinoir.models.user :as user])
  (:use [noir.core :only [defpage render]]
        mookinoir.views.layout))

(defpage "/login" {}
  (if (session/get :user)
    (resp/redirect "/home")
    (layout
      :login
      (-> "mookinoir/views/login.html"
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
          (resp/redirect "/home"))
        (render "/register" {})))))

(defpage logout "/logout"
  {}
  (session/remove! :user)
  (resp/redirect "/welcome"))
