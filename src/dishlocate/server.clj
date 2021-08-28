(ns dishlocate.server
  (:require [cheshire.core :as json])
  (:require [dishlocate.core :as core])
  (:use ring.adapter.jetty)
  (:use ring.middleware.params)
  (:use ring.util.response))

;; server

(defn find-dish [dish location]
  (let [places (core/find-dish dish location)]
    (str "{\"results\" : [" (json/generate-string places) "]}")))

(defn handler [{{dish "dish", location "location"} :params}]
  (-> (response (find-dish dish location))
    (content-type "application/json")))

(def server (-> handler wrap-params))

(run-jetty server {:port 8080})