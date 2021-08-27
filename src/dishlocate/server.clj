(ns dishlocate.server
  (:require [dishlocate.core :as core])
  (:use ring.adapter.jetty)
  (:use ring.middleware.params)
  (:use ring.util.response))

;; server

(defn to-json [place]
  (str "{\"id\" : \"" (get place :id) "\", \"name\" : \"" (get place :name) "\"},"))

(defn find-dish [dish location]
  (let [places (core/find-dish dish location)]
    (str "{\"results\" : [" (apply str (map to-json places)) "]}")))

(defn handler [{{dish "dish", location "location"} :params}]
  (-> (response (find-dish dish location))
    (content-type "application/json")))

(def server (-> handler wrap-params))

(run-jetty server {:port 8080})