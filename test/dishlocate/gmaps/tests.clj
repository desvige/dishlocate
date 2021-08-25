(ns dishlocate.gmaps.tests
  (:require [dishlocate.gmaps :as maps])
  (:use clojure.test))

;; find-restaurants

(defn get-text-search-request [location]
  "test/dishlocate/gmaps/text-search.json")

(deftest find-restaurants
  (with-redefs [maps/get-text-search-request get-text-search-request]
    (is (contains? (maps/find-restaurants "nearby")
      "ChIJyWEHuEmuEmsRm9hTkapTCrk"))))

;; get-menu-url

(defn get-place-details-request [place-id]
  "test/dishlocate/gmaps/place-details.json")

(deftest get-gmaps-url
  (with-redefs [maps/get-place-details-request get-place-details-request]
    (is (= (maps/get-menu-url ["foobar1337" "McDonald's"])
      "https://maps.google.com/?cid=5232738768037132369"))))