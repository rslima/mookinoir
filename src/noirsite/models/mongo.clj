(ns noirsite.models.mongo
  (:use [somnium.congomongo :only [make-connection]]))

(def conn (make-connection :mookirana :host "127.0.0.1" :port 27017))