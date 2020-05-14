(ns dishlocate.geolocation
  (:use clojure.test))

(defn mock-get-text-search-request [location]
  "test/geolocation/gmaps/text-search.json")

(deftest test-find-restaurants
  (with-redefs [get-text-search-request mock-get-text-search-request]
    (is (contains? (find-restaurants "nearby")
      "ChIJyWEHuEmuEmsRm9hTkapTCrk"))))

(run-tests)