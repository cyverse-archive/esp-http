(ns esp-http.init
  (:use esp-http.core
        esp-http.conf
        korma.db
        [clojure-commons.props :only [read-properties]]
        [clojure-commons.file-utils :only [exists? path-join]])
  (:require [immutant.messaging :as messaging]
            [immutant.web :as web]
            [immutant.utilities :as util]
            [immutant.repl :as repl]
            [clojure.tools.logging :as log]))

(init-config (get-properties))
(init-db)

;;;Make sure Korma closes DB connections cleanly at exit.
;;;(util/at-exit #(.close (:datasource @korma.db/_default)))

(web/start app)

(repl/start-nrepl 3333)

;; Messaging allows for starting (and stopping) destinations (queues & topics)
;; and listening for messages on a destination.

; (messaging/start "/queue/a-queue")
; (messaging/listen "/queue/a-queue" #(println "received: " %))

