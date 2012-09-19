(ns esp-http.conf)

(def config (atom nil))

(defn init-config
  [props]
  (reset! config props))

(defn db-name [] (get @config "esp.http.db.name"))

(defn db-user [] (get @config "esp.http.db.user"))

(defn db-password [] (get @config "esp.http.db.password"))

(defn db-host [] (get @config "esp.http.db.hostname"))

(defn db-port [] (get @config "esp.http.db.port"))