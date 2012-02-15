(ns noirsite.views.welcome
  (:require [noirsite.views.common :as common]
            [clojure.contrib.string :as str])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/welcome" []
  (common/layout
    :welcome
    [:h1 "Bem-vindo ao Mookirana"]
    [:p 
      (str/join " " [
        "Suas d&iacute;vidas est&atilde;o ficando fora de controle?"
        "Voc&ecirc; anda gastando muito com lazer?"
        "N&atilde;o sabe para onde vai o seu dinheiro?"
        "Gostaria de guardar dinheiro para uma viagem?"
        "De vez em quando, voc&ecirc; se esquece de pagar uma conta?"
        "O Mookirana pode te ajudar com estes e outros problemas!"])]))
