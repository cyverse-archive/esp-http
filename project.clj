(defproject esp-http "0.0.1-SNAPSHOT"
  :description "HTTP API into ESP"
  :dependencies
  [[org.clojure/clojure "1.4.0"]
   [org.clojure/data.json "0.1.1"]
   [org.clojure/tools.logging "0.2.3"]
   [compojure "1.0.1"]
   [slingshot "0.10.1"]
   [org.iplantc/kameleon "0.0.2-SNAPSHOT"]
   [org.iplantc/clojure-commons "1.2.0-SNAPSHOT"]]
  :repositories
  {"iplantCollaborative"
   "http://projects.iplantcollaborative.org/archiva/repository/internal/"})
