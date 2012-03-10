(ns noirsite.views.layout
  (:require [noirsite.config.sitemap :as sitemap]
            [noir.session :as session]
            [noir.validation :as vali]
            [net.cgrand.enlive-html :as html])
  (:use [noir.core :only [defpage]]))

(def layout-template 
  (-> "noirsite/views/layout.html" html/html-resource))
(defn error-message-inline-template [msg]
  (-> "noirsite/views/error_message_template.html"
      html/html-resource
      (html/select [:.help-inline])
      (html/transform [html/root] (html/content msg))))
(defn error-message-block-template [msg]
  (-> "noirsite/views/error_message_template.html"
      html/html-resource
      (html/select [:.help-block])
      (html/transform [html/root] (html/content msg))))

(defn field-selector [field]
  [(html/attr= :name (name field))])

(defn add-error-message-inline [field]
  (if (vali/errors? field)
    #(html/at %
       [[:div.control-group (html/has (field-selector field))]]
       (html/add-class "error")
       [[:div.controls (html/has (field-selector field))]]
       (html/append (error-message-inline-template (first (vali/get-errors field)))))
    identity))
(defn add-error-message-inline-checkbox [field]
  (if (vali/errors? field)
    #(html/at %
       [[:div.control-group (html/has (field-selector field))]]
       (html/add-class "error")
       [[:label (html/has (field-selector field))]]
       (html/append (error-message-inline-template (first (vali/get-errors field)))))
    identity))

(defn populate-text-field [field value]
  #(html/transform %
     (field-selector field)
     (html/set-attr :value value)))

(defn populate-checkbox-field [field value]
  (if value
    #(html/transform
       %
       (field-selector field)
       (html/set-attr :checked "checked"))
    identity))

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
                (:label (sitemap/principal pagina-atual)))))))
     (make-root [template]
       (html/transform template
         [:a.brand]
         (if (= pagina-atual :root)
           (html/add-class "active")
           identity)))
     (make-menu [template]
       (html/transform template
         [:ul#main-nav :> :li]
         (html/clone-for
          [[menu item] (sitemap/menu-principal)]
           [:li]
           (if (= pagina-atual menu)
             (html/add-class "active")
             identity)             
           [:a]
           (html/do->
            (html/set-attr :href (:link item))
            (html/content (:label item))))))
     (make-login [template]
       (if (session/get :user)
         (html/transform
           template
           [:.login-menu :a]
           (html/do->
             (html/set-attr :href "/logout")
             (html/content "Logout")))
         template))
     (make-transforms [template]
       ((->> transforms
             (concat [make-title make-root make-menu make-login])
             reverse
             (apply comp)) template))]
    (-> layout-template make-transforms html/emit*)))

(defn replace-container-div [snippet]
  #(html/transform %
     [:body :> :div.container]
     (html/substitute snippet)))

(defn select-container-div [template]
  (-> template html/html-resource (html/select [:div.container])))
