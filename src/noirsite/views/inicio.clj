(ns noirsite.views.inicio
  (:require [noirsite.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/inicio" []
  (common/layout
   :inicio
   [:h1 "Suas Informa&ccedil;&otilde;es"]))
