(ns dishlocate.geolocation
  (:require [cheshire.core :as json]))

(def api-key "insert Google API key here")

(defn read-json [get-json-request-fn args]
  (let [json-str (slurp (apply get-json-request-fn args))]
    (json/parse-string json-str true))) ; keywordize-keys