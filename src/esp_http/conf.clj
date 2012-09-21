(ns esp-http.conf
  (:use korma.db
        [korma.config :only [options]]
        [clojure-commons.props :only [read-properties]]
        [clojure-commons.file-utils :only [exists? path-join]])
  (:require [clojure.tools.logging :as log]
            [immutant.xa :as xa]))

(def config (atom nil))

(def homedir (System/getProperty "user.home"))

(defn get-properties
  []
  (let [dev-conf   (path-join homedir ".esp" "esp-http.properties")
        local-conf "/etc/esp/esp-http.properties"]
    (cond
     (exists? dev-conf)   (read-properties dev-conf)
     (exists? local-conf) (read-properties local-conf)
     :else                (log/error "No config found."))))

(defn db-name [] (get @config "esp.http.db.name"))

(defn db-user [] (get @config "esp.http.db.user"))

(defn db-password [] (get @config "esp.http.db.password"))

(defn db-host [] (get @config "esp.http.db.hostname"))

(defn db-port [] (get @config "esp.http.db.port"))

(defn init-config
  [props]
  (reset! config props))

(defonce immutant-datasource (atom nil))

(defn db-spec
  []
  {:adapter  "postgresql"
   :host     (db-host)
   :username (db-user)
   :password (db-password)
   :port     (db-port)
   :database (db-name)})

(defn init-db
  []
  (reset! immutant-datasource (xa/datasource "immutant-datasource" (db-spec)))
  (default-connection {:pool {:datasource @immutant-datasource}
                       :options @options}))