(ns esp-http.controllers
  (:require [clojure.data.json :as json]
            [kameleon.esp-queries :as espq]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-source-by-uuid
  "Returns a JSON string containing info about an event source. Looks up the
   event source by its UUID."
  [uuid]
  (json/json-str
   {:results (espq/query-event-sources [] :source_uuid uuid)}))

(defn get-source-by-tag
  "Returns a JSON string containing info about an event source. Looks up the
   event source by its tag. May return multiple event sources if the they each
   have the same tag associated with them."
  [tag]
  (json/json-str
   {:results (espq/query-event-sources [] :tag tag)}))

(defn get-event-by-uuid
  "Returns a JSON string containing info about an event. Looks up the event
   by its UUID."
  [uuid]
  (json/json-str
   {:results (espq/query-events [] :event_uuid uuid)}))

(defn add-event
  "Inserts a new event into the database and returns the info for the newly
   created event as a JSON string."
  [{:keys [source-uuid type data]}]
  (json/json-str
   {:results
    [(espq/insert-event (uuid) source-uuid type (json/json-str data))]}))

(defn add-event-source
  "Inserts a new event source into the database and returns the info for the
   newly created event source as a JSON string."
  [{:keys [tag data]}]
  (json/json-str
   {:results
    [(espq/insert-event-source (uuid) tag (json/json-str data))]}))