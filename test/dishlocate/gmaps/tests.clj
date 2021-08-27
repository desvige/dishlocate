(ns dishlocate.gmaps.tests
  (:require [dishlocate.gmaps :as maps])
  (:use clojure.test))

;; find-places

(def text-search-json "test/dishlocate/gmaps/text-search.json")

(deftest find-places
  (with-redefs [maps/get-text-search-request (constantly text-search-json)]
    (is (= (count (maps/find-places "nearby")) 4))))

;; get-menu-url

(def place-details-json "test/dishlocate/gmaps/place-details.json")

(deftest get-gmaps-url
  (with-redefs [maps/get-place-details-request (constantly place-details-json)]
    (is (= (maps/get-menu-url ["foobar1337" "McDonald's"])
      "https://maps.google.com/?cid=5232738768037132369"))))