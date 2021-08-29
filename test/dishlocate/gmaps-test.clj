(ns dishlocate.gmaps-test
  (:require [dishlocate.gmaps :as maps])
  (:use clojure.test))

;; find-places

(def search-json "test/dishlocate/text-search.json")

(deftest find-places
  (with-redefs [maps/get-search-request (constantly search-json)]
    (is (= (count (maps/find-places "nearby")) 4))))

;; get-menu-url

(def details-json "test/dishlocate/place-details.json")

(deftest get-gmaps-url
  (with-redefs [maps/get-details-request (constantly details-json)]
    (is (= (maps/get-menu-url ["foobar1337" "McDonald's"])
      "https://maps.google.com/?cid=5232738768037132369"))))