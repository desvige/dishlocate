(ns dishlocate.geolocation)

(load-file "src/geolocation/gmaps/common.clj")

(defn get-place-details-request [place-id]
  (let [parameters (str "key=" api-key "&place_id=" place-id "&fields=url")
        output "json"]
    (str "https://maps.googleapis.com/maps/api/place/details/"
      output "?" parameters)))

(defn read-place-details [place-id]
  (read-json get-place-details-request (list place-id)))

(defn get-gmaps-page-url [place-id]
  (let [result (get (read-place-details place-id) :result)]
    (get result :url)))

(defn read-gmaps-page [place-id]
  (slurp (get-gmaps-page-url place-id)))

(defn get-menu-url [[place-id name]]
  (get-gmaps-page-url place-id))