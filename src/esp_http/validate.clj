(ns esp-http.validate)

(defn uuid?
  [uuid]
  (pos?
   (count
    (re-seq #"[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}" uuid))))