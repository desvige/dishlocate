(ns dishlocate.core
  (:require [clojure.string :as str])
  (:require [dishlocate.geolocation :as geo]))

(defn has-dish? [dish place]
  (try
    (let [html (slurp (geo/get-menu-url place))]
      (str/includes? (str/lower-case html) dish))
    (catch Exception ex
      false)))

(defn find-dish [dish location]
  (let [places (geo/find-restaurants location)]
    (map geo/get-place-name
      (filter (partial has-dish? dish) places))))