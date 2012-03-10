(ns noirsite.views.register
  (:require [clojure.string :as str]
            [noir.response :as resp]
            [noir.session :as session]
            [noir.validation :as vali]
            [noirsite.models.user :as user])
  (:use  [noir.core :only [defpage render]]
         noirsite.views.layout))

(defn add-error-messages []
  (->>
    [:name :email :password :password2]
    (map add-error-message-inline)
    (apply comp (add-error-message-inline-checkbox :agree))))

(defn populate-register-form
  [{:keys [name email agree]}]
  (comp
    (populate-text-field :name name)
    (populate-text-field :email email)
    (populate-checkbox-field :agree agree)))

(defpage "/register"
  {:keys [name
          email
          agree]
   :or {name ""
        email ""
        agree false}}
    (layout
      :register
      (-> "noirsite/views/register.html"
          select-container-div
          replace-container-div)
      (populate-register-form {:name name :email email :agree agree})
      (add-error-messages)))

(defn register-form-valid?
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
     "Nome deve possuir no mínimo 2 caracteres"])
  (vali/rule
    (vali/is-email? email)
    [:email
     "E-mail inválido"])
  (vali/rule
    (not (user/fetch-user-by-email form))
    [:email
     "Email já cadastrado"])
  (vali/rule
    (vali/min-length? (str/trim password) 5)
    [:password
     "Senha deve ter no mínimo 5 caracteres"])
  (vali/rule
    (= password password2)
    [:password2
     "A senha repetida deve ser igual a original"])
  (vali/rule
    agree
    [:agree
     "Só é possível registrar estando de acordo com os termos de uso."])
  (not (vali/errors? :name :email :password :password2 :agree)))

(defpage [:post "/register"]
  form
  (if (register-form-valid? form)
    (do
      (session/put! :user (user/register! form))
      (resp/redirect "/inicio"))
    (render "/register" form)))

