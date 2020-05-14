(ns dishlocate.core
  (:use clojure.test))

(def chicken-places #{"KFC" "Popeyes"})
(def taco-places #{"Taco Bell"})

(defn mock-find-restaurants [location]
  (concat chicken-places taco-places))

(defn mock-get-place-name [place]
  place)

(defn mock-has-dish? [dish place]
  (case dish
    "chicken" (contains? chicken-places place)
    "tacos" (contains? taco-places place)
    false))

(deftest test-find-dishes
  (with-redefs [geo/find-restaurants mock-find-restaurants
                geo/get-place-name mock-get-place-name
                has-dish? mock-has-dish?]
    (is (= (set (find-dish "chicken" "nearby")) chicken-places))
    (is (= (set (find-dish "tacos" "nearby")) taco-places))
    (is (= (set (find-dish "korean" "nearby")) #{}))))

(deftest test-error-no-restaurants
  (with-redefs [geo/find-restaurants (fn [location] nil)
                geo/get-place-name mock-get-place-name
                has-dish? mock-has-dish?]
    (is (empty? (find-dish "whatever" "middle of nowhere")))))