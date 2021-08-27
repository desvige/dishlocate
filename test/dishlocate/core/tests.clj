(ns dishlocate.core.tests
  (:require [dishlocate.core :as core])
  (:require [dishlocate.gmaps :as maps])
  (:use clojure.test))

;; has-dish?

(def menu-url "test/dishlocate/core/menu.html")

(deftest has-dish
  (with-redefs [maps/get-menu-url (constantly menu-url)]
    (is (core/has-dish? "big mac" "mcdo"))
    (is (core/has-dish? "nuggets" "mcdo"))
    (is (core/has-dish? "burger" "mcdo"))))

(deftest has-not-dish
  (with-redefs [maps/get-menu-url (constantly menu-url)]
    (is (false? (core/has-dish? "korean" "mcdo")))))

(deftest no-menu-url
  (with-redefs [maps/get-menu-url (constantly nil)]
    (is (false? (core/has-dish? "sushi" "shithole")))))

;; find-dish

(def chicken-places #{"KFC" "Popeyes"})
(def taco-places #{"Taco Bell"})
(def places (concat chicken-places taco-places))

(defn has-dish? [dish place]
  (case dish
    "chicken" (contains? chicken-places place)
    "tacos" (contains? taco-places place)
    false))

(deftest find-dish
  (with-redefs [core/has-dish? has-dish?
                maps/arrange-data identity
                maps/find-places (constantly places)]
    (is (= (set (core/find-dish "chicken" "nearby")) chicken-places))
    (is (= (set (core/find-dish "tacos" "nearby")) taco-places))
    (is (= (set (core/find-dish "korean" "nearby")) #{}))))

(deftest no-restaurant
  (with-redefs [core/has-dish? has-dish?
                maps/find-places (constantly nil)]
    (is (empty? (core/find-dish "whatever" "middle of nowhere")))))