(ns noirsite.views.login-form
  (:use [noir.core :only [defpartial]]))

(defpartial login-form-navbar []  
  [:form.navbar-search.pull-right {:method "post" :action "/login"}
    [:input.input-small {:type "text" :name "email" :placeholder "E-mail"}]
    [:input.input-small {:type "password" :name "senha" :placeholder "Senha"}]
    [:input.btn-primary {:type "submit" :value "Login"}]])
