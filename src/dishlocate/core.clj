(ns dishlocate.core
  (:require [clojure.string :as str])
  (:require [dishlocate.gmaps :as maps])
  (:require [dishlocate.utils :as utils]))

;; find-dish

(defn has-dish? [dish place]
  (try
    (let [html (slurp (maps/get-menu-url place))]
      (str/includes? (str/lower-case html) dish))
    (catch Exception ex
      false)))

(defn find-places-with-dish [dish location]
  (let [places (maps/find-places location)]
    (utils/pfilter (partial has-dish? dish) places)))  ; parallel-filter

(defn find-dish [dish location]
  (let [places (find-places-with-dish dish location)]
    (map maps/arrange-data places)))