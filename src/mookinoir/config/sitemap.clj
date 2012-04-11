(ns mookinoir.config.sitemap
  (:use [clojure.set :only [intersection]]))
(def main 
  { 
   :welcome {
     :label "Welcome"
     :link "/welcome"
   }
   :login {
     :label "Login"
     :link "/login"
   }
   :register {
     :label "Register"
     :link "/register"
   }
   :home {
     :label "Home"
     :link "/home"
     :visible true
     :order 0
     :roles {
       :get #{ :user }
     }
   }
   :accounts {
     :label "Accounts"
     :link "/accounts"
     :visible true
     :order 1
     :roles {
       :get #{ :user }
     }
   }
   :schedule {
     :label "Schedule"
     :link "/schedule"
     :visible true
     :order 2
     :roles {
       :get #{ :user }
     }
   }    
   :budget {
     :label "Budget"
     :link "/budget"
     :visible true
     :order 3
     :roles {
       :get #{ :user }
     }
   }
   :system {
     :label "System"
     :link "/system"
     :visible true
     :order 10
     :roles {
       :get #{ :admin }
     }
   }
  })

(defn visible? [[menu item]]
  (:visible item))

(defn public? [item]
  (not (:roles item)))

(defn is-authorized? [user {:keys [method resource] :as action}]
  (or (public? (main resource))
      (not (empty?
         (intersection
           (-> main resource :roles method)
           (->> user :roles (map keyword) set))))))

(defn main-menu [user]
  (letfn
    [(has-order? [[menu item]]
       (contains? item :order))
     (order [[menu item]]
       (:order item))
     (filter-auth [[menu _]]
       (is-authorized? user { :method :get :resource menu }))]
    (->> main
         (filter filter-auth)
         (filter has-order?)
         (sort-by order <))))

