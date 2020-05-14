(ns dishlocate.geolocation
  (:require [clojure.string :as str]))

(load-file "src/geolocation/gmaps/common.clj")

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