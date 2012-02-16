(ns noirsite.views.layout
  (:require [noirsite.models.menu :as menu]
            [noir.session :as session]
            [net.cgrand.enlive-html :as html])
  (:use [noir.core :only [defpage]]))

(def layout-template (html/html-resource "noirsite/views/layout.html"))

(defn layout [pagina-atual & transforms]
  (letfn
    [(make-title [template]
         (html/transform template
           [:title]
           (if (= pagina-atual :root)
             (html/content "Mookirana")
             (html/content
              (str
                "Mookirana - "
                (:label (menu/encontra-menu pagina-atual)))))))
     (make-root [template]
       (html/transform template
         [:a.brand]
         (if (= pagina-atual :root)
           (html/add-class "active")
           identity)))
     (make-menu [template]
       (html/transform template
         [:ul.nav :> :li]
         (html/clone-for [item (filter menu/visivel menu/principal)]
           [:li]
           (if (= pagina-atual (:menu item))
             (html/add-class "active")
             identity)              
           [:a]
           (html/do->
            (html/set-attr :href (:link item))
            (html/content (:label item))))))           
     (make-transforms [template]
        ((apply comp make-title make-root make-menu transforms) template))]
    (-> layout-template make-transforms html/emit*)))

(defn replace-container-div [snippet]
  #(html/transform %
     [:body :> :div.container]
     (html/substitute snippet)))

(defpage "/layout" []
  (layout :contas))
