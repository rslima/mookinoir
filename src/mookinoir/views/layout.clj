(ns mookinoir.views.layout
  (:require [mookinoir.config.sitemap :as sitemap]
            [noir.session :as session]
            [noir.validation :as vali]
            [net.cgrand.enlive-html :as html])
  (:use [noir.core :only [defpage]]
        mookinoir.config.configuration))

(def ^:private layout-template 
  (-> "mookinoir/views/layout.html" html/html-resource))

(declare make-transforms)

(defn layout [current-page & transforms]
  (-> layout-template (make-transforms current-page transforms) html/emit*))

(declare basic-layout-functions)

(defn ^:private make-transforms [template current-page transforms]
  ((->>
    transforms
    (concat (basic-layout-functions current-page))
    reverse
    (apply comp)) template))

(declare
  make-title
  make-root
  make-menu
  make-login)

(defn ^:private basic-layout-functions [current-page]
  (map apply [make-title make-root make-menu make-login] (repeat [current-page])))

(defn ^:private make-title [current-page]
  #(html/transform %
    [:title]
    (if (= current-page :root)
      (html/content (:site-name *configuration*))
      (html/content
        (str
          (:site-name *configuration*)
          " - "
          (:label (sitemap/main current-page)))))))

(defn ^:private make-root [current-page]
  #(html/transform %
     [:a.brand]
     (if (= current-page :root)
       (html/add-class "active")
       identity)))

(defn ^:private make-menu [current-page]
  #(let
     [user (session/get :user)]
     (html/transform %
       [:ul#main-nav :> :li]
       (html/clone-for [[menu item] (sitemap/main-menu user)]
         [:li]
         (if (= current-page menu)
           (html/add-class "active")
           identity)             
         [:a]
         (html/do->
           (html/set-attr :href (:link item))
           (html/content (:label item)))))))

(defn ^:private make-login [current-page]
  #(if (session/get :user)
    (html/transform %
      [:.login-menu :a]
      (html/do->
        (html/set-attr :href "/logout")
        (html/content "Logout")))
    %))

(defn replace-container-div [snippet]
  #(html/transform %
     [:body :> :div.container]
     (html/substitute snippet)))

(defn select-container-div [template]
  (-> template html/html-resource (html/select [:div.container])))

(defn field-selector [field]
  [(html/attr= :name (name field))])

(defn error-message-inline-template [msg]
  (-> "mookinoir/views/error_message_template.html"
      html/html-resource
      (html/select [:.help-inline])
      (html/transform [html/root] (html/content msg))))

(defn error-message-block-template [msg]
  (-> "mookinoir/views/error_message_template.html"
      html/html-resource
      (html/select [:.help-block])
      (html/transform [html/root] (html/content msg))))

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
