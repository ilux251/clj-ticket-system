(ns ticket-system.server.handler
  (:require [compojure.core :as compojure :refer [GET]]
            [compojure.route :as route]
            [ring.middleware.defaults :as ring]
            [ticket-system.server.adapter :as adapter]
            [ring.middleware.json :refer [wrap-json-response]]))

(defn- routes
  []
  (compojure/routes
   (GET "/ticket/all" [] {:op [:all-tickets]})
   (GET "/ticket/get" [ticket-nr] {:op [:get-ticket ticket-nr]})))

;; Public

(defn api-handler
  []
  (-> (routes)
      (adapter/adapter-handler)
      (wrap-json-response)
      (ring/wrap-defaults ring/api-defaults)))

(comment
  (require '[ring.mock.request :as mock])
  
  (let [body (-> (mock/request :get "/ticket/get" (array-map :ticket-nr "TICKET-1"))
                 ((api-handler))
                 :body)]
    body)
  
  (let [body (-> (mock/request :get "/ticket/all")
                 ((api-handler))
                 :body)]
    body)
  )