(ns noirsite.views.login
  (:require [noirsite.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/login" []
  (common/layout
   :login
   [:h1 "Suas Informa&ccedil;&otilde;es"]))
