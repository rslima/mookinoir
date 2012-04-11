(ns mookinoir.views.register
  (:require [clojure.string :as str]
            [noir.response :as resp]
            [noir.session :as session]
            [noir.validation :as vali]
            [mookinoir.models.user :as user])
  (:use  [noir.core :only [defpage render]]
         mookinoir.views.layout))

(declare populate-register-form)
(declare add-error-messages)

(defpage "/register"
  {:keys [name
          email
          agree]
   :or {name ""
        email ""
        agree false}}
    (layout
      :register
      (-> "mookinoir/views/register.html"
          select-container-div
          replace-container-div)
      (populate-register-form {:name name :email email :agree agree})
      (add-error-messages)))

(defn ^:private populate-register-form
  [{:keys [name email agree]}]
  (comp
    (populate-text-field :name name)
    (populate-text-field :email email)
    (populate-checkbox-field :agree agree)))

(defn ^:private add-error-messages []
  (->>
    [:name :email :password :password2]
    (map add-error-message-inline)
    (apply comp (add-error-message-inline-checkbox :agree))))

(declare valid?)

(defpage post-register [:post "/register"]
  form
  (if (valid? form)
    (do
      (session/put! :user (user/register! form))
      (resp/redirect "/home"))
    (render "/register" form)))

(defn ^:private valid?
  [{:keys [name
           email
           password
           password2
           agree]
    :or {agree false}
    :as form}]
  (vali/rule
    (vali/min-length? (str/trim name) 2)
    [:name
     "Name must be at least 2 characters long"])
  (vali/rule
    (vali/is-email? email)
    [:email
     "Invalid e-mail"])
  (vali/rule
    (not (user/fetch-user-by-email form))
    [:email
     "e-mail already registered"])
  (vali/rule
    (vali/min-length? (str/trim password) 5)
    [:password
     "Password must be at least 5 characters long"])
  (vali/rule
    (= password password2)
    [:password2
     "Both passwords must match"])
  (vali/rule
    agree
    [:agree
     "You can only register if you agree with the usage terms."])
  (not (vali/errors? :name :email :password :password2 :agree)))
