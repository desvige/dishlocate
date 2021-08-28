(ns dishlocate.gmaps
  (:require [clojure.string :as str])
  (:require [dishlocate.gmaps.private :as priv])
  (:require [dishlocate.utils :as utils]))

;; find-places

(defn get-search-params [location]
  (str "key=" priv/api-key "&query=restaurants%20"
    (str/replace location #" " "%20")))

(defn get-search-request [location]
  (let [params (get-search-params location)]
    (str "https://maps.googleapis.com/maps/api/place/textsearch/json?" params)))

(defn read-search [location]
  (utils/read-json (get-search-request location)))

(defn find-places [location]
  (get (read-search location) :results))

;; get-menu-url

(defn get-details-params [place-id]
  (str "key=" priv/api-key "&place_id=" place-id "&fields=url"))

(defn get-details-request [place-id]
  (let [params (get-details-params place-id)]
    (str "https://maps.googleapis.com/maps/api/place/details/json?" params)))

(defn read-details [place-id]
  (utils/read-json (get-details-request place-id)))

(defn get-gmaps-url [place-id]
  (let [result (get (read-details place-id) :result)]
    (get result :url)))

(defn get-menu-url [place]
  (let [place-id (get place :place_id)]
    (get-gmaps-url place-id)))

;; arrange-data

(defn arrange-data [place]
  {:id (get place :place_id), :name (get place :name),
    :address (get place :formatted_address),
    :opened (get (get place :opening_hours) :open_now)})