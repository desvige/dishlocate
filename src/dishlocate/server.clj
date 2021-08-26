(ns dishlocate.server
  (:require [dishlocate.core :as core])
  (:use ring.adapter.jetty)
  (:use ring.middleware.params)
  (:use ring.util.response))

(defn jsonize [[place-id name]]
  (str "{\"id\" : \"" place-id "\", \"name\" : \"" name "\"},"))

(defn find-dish [dish location]
  (let [places (core/find-dish dish location)]
    (str "{\"results\" : ["
      (apply str
        (map jsonize places))
      "]}")))

(defn handler [{{dish "dish", location "location"} :params}]
  (-> (response (find-dish dish location))
    (content-type "application/json")))

(def app
  (-> handler wrap-params))

(run-jetty app {:port 8080})