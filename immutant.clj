(ns esp-http.init
  (:use esp-http.core
        esp-http.conf
        korma.db
        [clojure-commons.props :only [read-properties]]
        [clojure-commons.file-utils :only [exists? path-join]])
  (:require [immutant.messaging :as messaging]
            [immutant.web :as web]
            [immutant.utilities :as util]
            [clojure.tools.logging :as log]))

(def homedir (System/getProperty "user.home"))

(defn get-properties
  []
  (let [dev-conf   (path-join homedir ".esp" "esp-http.properties")
        local-conf "/etc/esp/esp-http.properties"]
    (cond
     (exists? dev-conf)   (read-properties dev-conf)
     (exists? local-conf) (read-properties local-conf)
     :else                (log/error "No config found."))))

(init-config (get-properties))

(defdb pg
  (postgres
   {:db       (db-name)
    :user     (db-user)
    :password (db-password)
    :host     (db-host)
    :port     (db-port)}))

(web/start app)

;; Messaging allows for starting (and stopping) destinations (queues & topics)
;; and listening for messages on a destination.

; (messaging/start "/queue/a-queue")
; (messaging/listen "/queue/a-queue" #(println "received: " %))

