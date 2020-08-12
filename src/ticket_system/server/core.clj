(ns ticket-system.server.core
  (:require [org.httpkit.server :as server]
            [ring.middleware.defaults :as ring]
            [ticket-system.server.handler :as handler]))

(defn -main
  [& _args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5000"))]
    (server/run-server (handler/api-handler) {:port port})
    (println "server run on port " port)))