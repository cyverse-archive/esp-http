(ns esp-http.controllers
  (:use esp-http.validate
        [slingshot.slingshot :only [try+ throw+]])
  (:require [clojure.data.json :as json]
            [kameleon.esp-queries :as espq]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn bad-uuid
  [uuid]
  (throw+ {:error_code "ERR_NOT_UUID"
           :uuid uuid}))

(defn bad-string
  [field in-val]
  (throw+ {:error_code "ERR_NOT_STRING"
           :field field
           :value in-val}))

(defn bad-data
  [field data]
  (throw+ {:error_Code "ERR_BAD_DATA"
           :field field
           :data data}))

(defn get-source-by-uuid
  "Returns a JSON string containing info about an event source. Looks up the
   event source by its UUID."
  [uuid]
  (when-not (uuid? uuid) (bad-uuid uuid))
  
  (json/json-str
   {:results (espq/query-event-sources [] :source_uuid uuid)}))

(defn get-source-by-tag
  "Returns a JSON string containing info about an event source. Looks up the
   event source by its tag. May return multiple event sources if the they each
   have the same tag associated with them."
  [tag]
  (when-not (string? tag) (bad-string "tag" tag))
  
  (json/json-str
   {:results (espq/query-event-sources [] :tag tag)}))

(defn get-event-by-uuid
  "Returns a JSON string containing info about an event. Looks up the event
   by its UUID."
  [uuid]
  (when-not (uuid? uuid) (bad-uuid uuid))
  
  (json/json-str
   {:results (espq/query-events [] :event_uuid uuid)}))

(defn add-event
  "Inserts a new event into the database and returns the info for the newly
   created event as a JSON string."
  [{:keys [source-uuid type data]}]
  (when-not (uuid? source-uuid) (bad-uuid source-uuid))
  (when-not (string? type) (bad-string "type" type))
  (when-not (map? data) (bad-data "data" data))
  
  (json/json-str
   {:results
    [(espq/insert-event (uuid) source-uuid type (json/json-str data))]}))

(defn add-event-source
  "Inserts a new event source into the database and returns the info for the
   newly created event source as a JSON string."
  [{:keys [tag data]}]
  (when-not (string? tag) (bad-string "tag" tag))
  (when-not (map? data) (bad-data "data" data))
  
  (json/json-str
   {:results
    [(espq/insert-event-source (uuid) tag (json/json-str data))]}))
