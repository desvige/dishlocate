(ns dishlocate.core)

(load-file "src/geolocation/dummy.clj")
(load-file "src/core/find-dish.clj")

(load-file "test/core/find-dish-test.clj")
(load-file "test/core/has-dish-test.clj")

(run-tests)