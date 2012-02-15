(ns noirsite.views.orcamento
  (:require [noirsite.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/orcamento" []
  (common/layout
   :orcamento
   [:h1 "Suas Informa&ccedil;&otilde;es"]))
