(ns mookinoir.models.crypto
  (:require [crypto.random :as c])
  (:use clj-message-digest.core)
  (:import org.apache.commons.codec.binary.Base64))

(defn create-salt []
  (c/bytes 8))

(defn decode-base64-string [s]
  (Base64/decodeBase64 s))

(defn encode-base64-bytes [bs]
  (Base64/encodeBase64String bs))

(defn encode-password [password salt]
  (-> password .getBytes (concat salt) byte-array sha-512-base64))
