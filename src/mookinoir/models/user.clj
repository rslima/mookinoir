(ns mookinoir.models.user
  (:require [somnium.congomongo :as m])
  (:use clojure.set
        mookinoir.models.mongo
        mookinoir.models.crypto))

(defn- fetch-user-by-email-cmd [email]
  (when-let [user (m/fetch-one :users :where {:email email})]
    (merge user {:roles (->> user :roles set)})))

(defn fetch-user-by-email [{:keys [email]}]
  (m/with-mongo conn
    (fetch-user-by-email-cmd email)))

(defn authenticate [{:keys [email password]}]
  (m/with-mongo conn
    (when-let [user (fetch-user-by-email-cmd email)]
      (let [salt (decode-base64-string (:salt user))
            encoded-password (encode-password password salt)]
        (if (= (:password user) encoded-password)
          user
          nil)))))

(defn register! [{:keys [name email password]}]
  (m/with-mongo conn
    (let [user (fetch-user-by-email-cmd email)]
      (when-not user
        (let [salt (create-salt)
              encoded-password (encode-password password salt)
              base64-salt (encode-base64-bytes salt)]
          (m/insert!
           :users
           {:name name
            :email email
            :password encoded-password
            :salt base64-salt
            :roles #{"user"}}))))))

(defn- change-role! [merge-fn {:keys [email]} role]
  (m/with-mongo conn
    (when-let [user (fetch-user-by-email-cmd email)]
      (m/update! :users user (merge-with merge-fn user {:roles #{role}})))))

(def add-role! (partial change-role! union))

(def remove-role! (partial change-role! difference))
