(ns mookinoir.views.security
  (:require [noir.session :as session]
            [noir.response :as response])
  (:use noir.core))

(defn make-security-middleware
  "Calls authorization function and executes the appropiate action."
  [handler authorization-fn]
  (fn [request]
      (let
        [user (session/get :user)
         authorization (authorization-fn user request)]
        (cond
          (= authorization :redirect-to-login)
            (response/redirect "/login")
          (= authorization :ok)
            (handler request)
          :else
            (response/status 403 "forbidden")))))

(defn match-page-and-user-roles
  [user request]
  (println "\n-------")
  (println @noir-routes)
  (println user)
  (println request)
  (println "-------")
  
  :ok)

