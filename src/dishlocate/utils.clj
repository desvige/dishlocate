(ns dishlocate.utils
  (:require [cheshire.core :as json]))

;; pfilter

(defn pfilter [f items]
  (let [filter (fn [item] (if (f item) [item]))]
    (apply concat (pmap filter items))))

;; read-json

(defn read-json [get-json-request-fn args]
  (let [json-str (slurp (apply get-json-request-fn args))]
    (json/parse-string json-str true)))  ; keywordize-keys