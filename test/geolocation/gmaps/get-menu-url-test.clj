(ns dishlocate.geolocation
  (:use clojure.test))

(defn mock-get-place-details-request [place-id]
  "test/geolocation/gmaps/place-details.json")

(deftest test-get-gmaps-page-url
  (with-redefs [get-place-details-request mock-get-place-details-request]
    (is (= (get-gmaps-page-url "foobar1337")
      "https://maps.google.com/?cid=5232738768037132369"))))

; (defn mock-get-gmaps-page-url [place-id]
;   "test/geolocation/gmaps/gmaps-page.html")

; (deftest test-get-menu-url
;   (with-redefs [get-gmaps-page-url mock-get-gmaps-page-url]
;     (is (= (get-menu-url "foobar1337")
;       "https://www.mcdonalds.fr/produits/menus"))))

; TODO: Test when gmaps-page-url is nil