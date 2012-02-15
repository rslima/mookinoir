(ns noirsite.views.agenda
  (:require [noirsite.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/agenda" []
  (common/layout
   :agenda
   [:h1 "Suas Informa&ccedil;&otilde;es"]))
