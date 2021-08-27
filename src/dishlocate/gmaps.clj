(ns dishlocate.gmaps
  (:require [clojure.string :as str])
  (:require [dishlocate.gmaps.private :as priv])
  (:require [dishlocate.utils :as utils]))

;; find-places

(defn get-text-search-request [location]
  (let [parameters (str "key=" priv/api-key
          "&query=restaurants%20" (str/replace location #" " "%20"))
        output "json"]
    (str "https://maps.googleapis.com/maps/api/place/textsearch/"
      output "?" parameters)))

(defn read-text-search [location]
  (utils/read-json get-text-search-request (list location)))

(defn find-places [location]
  (get (read-text-search location) :results))

;; get-menu-url

(defn get-place-details-request [place-id]
  (let [parameters (str "key=" priv/api-key "&place_id=" place-id "&fields=url")
        output "json"]
    (str "https://maps.googleapis.com/maps/api/place/details/"
      output "?" parameters)))

(defn read-place-details [place-id]
  (utils/read-json get-place-details-request (list place-id)))

(defn get-gmaps-url [place-id]
  (let [result (get (read-place-details place-id) :result)]
    (get result :url)))

(defn get-menu-url [place]
  (let [place-id (get place :place_id)]
    (get-gmaps-url place-id)))

;; arrange-data

(defn arrange-data [place]
  {:id (get place :place_id), :name (get place :name),
    :address (get place :formatted_address),
    :opened (get (get place :opening_hours) :open_now)})