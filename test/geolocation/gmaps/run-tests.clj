(ns dishlocate.geolocation)

(load-file "src/geolocation/gmaps.clj")

(load-file "test/geolocation/gmaps/find-restaurants-test.clj")
(load-file "test/geolocation/gmaps/get-menu-url-test.clj")

(run-tests)