(ns dishlocate.utils)

;; pfilter

(defn pfilter [f items]
  (let [filter (fn [item] (if (f item) [item]))]
    (apply concat (pmap filter items))))