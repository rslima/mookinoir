(ns noirsite.models.menu)
(def principal 
  [ 
    { :menu :welcome :label "Bem-vindo" :link "/welcome" }
    { :menu :login :label "Login" :link "/login" }
    { :menu :register :label "Registro" :link "/register" }
    { :menu :inicio :label "In&iacute;cio" :link "/inicio" :visible true }
    { :menu :contas :label "Contas" :link "/contas" :visible true}
    { :menu :agenda :label "Agenda" :link "/agenda" :visible true}
    { :menu :template :label "Enlive" :link "/template" :visible true}
    { :menu :orcamento :label "Or&ccedil;amento" :link "/orcamento" :visible true}])
(defn encontra-menu [menu]
  (first (filter #(= menu (:menu %)) principal)))

(defn visivel [item]
  (:visible item))
