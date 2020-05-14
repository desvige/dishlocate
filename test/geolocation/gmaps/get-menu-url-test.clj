(ns dishlocate.geolocation
  (:use clojure.test))

(defn mock-get-place-details-request [place-id]
  "test/geolocation/gmaps/place-details.json")

(deftest test-get-gmaps-page-url
  (with-redefs [get-place-details-request mock-get-place-details-request]
    (is (= (get-gmaps-page-url "foobar1337")
      "https://maps.google.com/?cid=5232738768037132369"))))
