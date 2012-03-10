(ns noirsite.config.sitemap)
(def principal 
  { 
    :welcome { :label "Bem-vindo" :link "/welcome" }
    :login { :label "Login" :link "/login" }
    :register { :label "Registro" :link "/register" }
    :inicio { :label "Início" :link "/inicio" :visible true :order 0 }
    :contas { :label "Contas" :link "/contas" :visible true :order 1 }
    :agenda { :label "Agenda" :link "/agenda" :visible true :order 2 }    
    :orcamento { :label "Orçamento" :link "/orcamento" :visible true :order 3 }})

(defn visivel? [[menu item]]
  (:visible item))

(defn menu-principal []
  (letfn
    [(has-order? [[menu item]]
       (contains? item :order))
     (order [[menu item]]
       (:order item))]
    (sort-by order < (filter has-order? principal))))