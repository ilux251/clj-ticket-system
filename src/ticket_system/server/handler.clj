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
   (GET "/ticket/ticketByTicketNr" [ticket-nr] {:op [:get-ticket ticket-nr]})
   (GET "/ticket/notesByTicketId" [ticket-id] {:op [:notes-by-ticket-id ticket-id]})
   (GET "/ticket/countNotesByTicket" [] {:op [:count-notes-by-ticket-nr]})))

(def ^:private cors-headers
  {"Access-Control-Allow-Origin"  "http://localhost:9500"
   "Access-Control-Allow-Headers" "*"
   "Access-Control-Allow-Methods" "GET, POST, OPTIONS"})

(defn- cors-handler
  [handler]
  (fn [request]
    (let [response (handler request)]
      (assoc response :headers cors-headers))))

;; Public

(defn api-handler
  []
  (-> (routes)
      (adapter/adapter-handler)
      (wrap-json-response)
      (ring/wrap-defaults ring/api-defaults)
      (cors-handler)))

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