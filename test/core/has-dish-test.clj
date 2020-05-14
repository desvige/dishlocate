(ns dishlocate.core
  (:use clojure.test))

(defn mock-get-menu-url [place]
  "test/core/menu.html")

(deftest test-has-dish
  (with-redefs [geo/get-menu-url mock-get-menu-url]
    (is (has-dish? "big mac" "mcdo"))
    (is (has-dish? "nuggets" "mcdo"))
    (is (has-dish? "burger" "mcdo"))))

(deftest test-hasnt-dish
  (with-redefs [geo/get-menu-url mock-get-menu-url]
    (is (false? (has-dish? "korean" "mcdo")))))

(deftest test-error-no-menu-url
  (with-redefs [geo/get-menu-url (fn [place] nil)]
    (is (false? (has-dish? "sushi" "shithole")))))