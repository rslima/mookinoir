(ns noirsite.views.contas
  (:require [noirsite.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/contas" []
  (common/layout
   :contas
   [:h1 "Suas Informa&ccedil;&otilde;es"]))
