(ns dishlocate.gmaps
  (:require [clojure.string :as str])
  (:require [cheshire.core :as json]))

(def api-key "<insert key here>")

(defn read-json [get-json-request-fn args]
  (let [json-str (slurp (apply get-json-request-fn args))]
    (json/parse-string json-str true)))  ; keywordize-keys

;; find-restaurants

(defn get-text-search-request [location]
  (let [parameters (str "key=" api-key
          "&query=restaurants%20" (str/replace location #" " "%20"))
        output "json"]
    (str "https://maps.googleapis.com/maps/api/place/textsearch/"
      output "?" parameters)))

(defn read-text-search [location]
  (read-json get-text-search-request (list location)))

(defn find-restaurants [location]
  (let [results (get (read-text-search location) :results)]
    (apply merge
      (map (fn [result]
        (let [place-id (get result :place_id)
              name (get result :name)]
          (hash-map place-id name)))
        results))))

;; get-menu-url

(defn get-place-details-request [place-id]
  (let [parameters (str "key=" api-key "&place_id=" place-id "&fields=url")
        output "json"]
    (str "https://maps.googleapis.com/maps/api/place/details/"
      output "?" parameters)))

(defn read-place-details [place-id]
  (read-json get-place-details-request (list place-id)))

(defn get-gmaps-url [place-id]
  (let [result (get (read-place-details place-id) :result)]
    (get result :url)))

(defn get-menu-url [[place-id name]]
  (get-gmaps-url place-id))