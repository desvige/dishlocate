(ns dishlocate.utils
  (:require [cheshire.core :as json]))

;; pfilter

(defn pfilter [f items]
  (let [filter (fn [item] (if (f item) [item]))]
    (apply concat (pmap filter items))))

;; read-json

(defn read-json [uri]
  (json/parse-string (slurp uri) true))  ; keywordize-keys