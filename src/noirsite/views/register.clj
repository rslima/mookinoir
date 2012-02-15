(ns noirsite.views.register
  (:require [noirsite.views.common :as common])
  (:use 
	[noir.core :only [defpartial defpage]]
    hiccup.form-helpers))

(defpartial register-form []
  (form-to [:post "/register"]
    [:div.campo
      (label "nome" "Nome")
      (text-field "nome")]
    [:div.campo 
      (label "email" "e-mail")
      (text-field "email")]
    [:div.campo 
      (label "senha" "Senha")
      (password-field "senha")]
    [:div.campo 
      (label "repita-senha" "Repita a Senha")
      (password-field "repita-senha")]
    [:div.campo
      (submit-button "Registrar")]))

(defpage "/register" []
  (common/layout
    :register
    [:h2 "Cadastre-se"]
    [:div#cadastro (register-form)]))
