(ns dishlocate.core.tests
  (:require [dishlocate.core :as core])
  (:require [dishlocate.gmaps :as maps])
  (:use clojure.test))

;; has-dish?

(defn get-menu-url [place]
  "test/dishlocate/core/menu.html")

(deftest has-dish
  (with-redefs [maps/get-menu-url get-menu-url]
    (is (core/has-dish? "big mac" "mcdo"))
    (is (core/has-dish? "nuggets" "mcdo"))
    (is (core/has-dish? "burger" "mcdo"))))

(deftest has-not-dish
  (with-redefs [maps/get-menu-url get-menu-url]
    (is (false? (core/has-dish? "korean" "mcdo")))))

(deftest no-menu-url
  (with-redefs [maps/get-menu-url (fn [place] nil)]
    (is (false? (core/has-dish? "sushi" "shithole")))))

;; find-dish

(def chicken-places #{"KFC" "Popeyes"})
(def taco-places #{"Taco Bell"})

(defn find-restaurants [location]
  (concat chicken-places taco-places))

(defn has-dish? [dish place]
  (case dish
    "chicken" (contains? chicken-places place)
    "tacos" (contains? taco-places place)
    false))

(deftest find-dish
  (with-redefs [maps/find-restaurants find-restaurants
                core/has-dish? has-dish?]
    (is (= (set (core/find-dish "chicken" "nearby")) chicken-places))
    (is (= (set (core/find-dish "tacos" "nearby")) taco-places))
    (is (= (set (core/find-dish "korean" "nearby")) #{}))))

(deftest no-restaurant
  (with-redefs [maps/find-restaurants (fn [location] nil)
                core/has-dish? has-dish?]
    (is (empty? (core/find-dish "whatever" "middle of nowhere")))))