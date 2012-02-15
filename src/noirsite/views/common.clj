(ns noirsite.views.common
  (:require [noirsite.models.menu :as menu]
            [noir.session :as session])
  (:use [noir.core :only [defpartial]]
        noirsite.views.login-form
        [net.cgrand.enlive-html :only [at emit* unwrap]]
        [hiccup.page-helpers :only [include-css include-js html5]]))

(defpartial layout [pagina-atual & content]
  (letfn
      [(make-root [nome]
         (if (= pagina-atual :root)
           [:a.brand.active {:href "/"} nome]
           [:a.brand {:href "/"} nome]))
       (make-menu-item [item]
         (let
           [active-class            
            (if (= pagina-atual (:menu item))
              { :class "active" }
              {})]
           [:li active-class
            [:a {:href (:link item)} (:label item)]]))
       (make-menu [menu]
         [:ul.nav
          (->> menu (filter menu/visivel) (map make-menu-item))])
       (make-title []
         (if (= pagina-atual :inicio)
           [:title "Mookirana"]
           [:title
            (str
              "Mookirana - "
              (:label
                (menu/encontra-menu pagina-atual)))]))]
    (html5
      [:head
        (make-title)
        (include-css 
          "/css/bootstrap.css"
          "/css/bootstrap-responsive.css")]
      [:body
        [:div.navbar
          [:div.navbar-inner
            [:div.container
              (make-root "Mookirana")
              (make-menu menu/principal)
              (login-form-navbar)]]]                
        [:div.container content]
        (include-js "/js/bootstrap.js")])))

(defn- unwrap-container-div [snippet]
  (at snippet
      [:div.container] unwrap))

(defn render-snippet [snippet]
  (->> snippet unwrap-container-div emit* (apply str)))