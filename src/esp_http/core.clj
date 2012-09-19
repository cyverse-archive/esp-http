(ns esp-http.core
  (:use compojure.core
        clojure-commons.error-codes
        esp-http.controllers
        [ring.middleware
         params
         keyword-params
         nested-params
         multipart-params])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [esp-http.json-body :as jb]
            [clojure-commons.query-params :as qp]
            [clojure.data.json :as json]
            [kameleon.esp-queries :as espq]))

(defroutes esp-http-routes
  (GET "/" [] "HURRRR")

  (GET "/source/tag/:tag" [tag]
       (trap "get-source-by-tag" get-source-by-tag tag))
  
  (GET "/source/:uuid" [uuid]
       (trap "get-source" get-source-by-uuid uuid))

  (GET "/event/:uuid" [uuid]
       (trap "get-event" get-event-by-uuid uuid))

  (PUT "/source" {body :body}
       (trap "add-source" add-event-source body))

  (PUT "/event" {body :body}
       (trap "add-event" add-event body))
  
  (POST "/test" {body :body}
        (trap "test" json/json-str body))

  (route/not-found "Page Not Found"))

(defn site-handler [routes]
  (-> routes
      jb/parse-json-body
      wrap-multipart-params
      wrap-keyword-params
      wrap-nested-params
      qp/wrap-query-params
      wrap-errors))

(def app (site-handler esp-http-routes))
