(ns ticket-system.server.operator.ticket
  (:require [ticket-system.server.sql.ticket :as ticket]))

(defn- ticket-operation
  [op & _params]
  op)

(defmulti ticket-operator ticket-operation)

(defmethod ticket-operator :all-tickets
  [_]
  (ticket/all-tickets))

(defmethod ticket-operator :get-ticket
  [_ ticket-nr]
  (ticket/ticket-by-ticket-nr ticket-nr))

(defmethod ticket-operator :notes-by-ticket-id
  [_ ticket-id]
  (ticket/notes-by-ticket-id ticket-id))

(defmethod ticket-operator :count-notes-by-ticket-nr
  [_]
  (ticket/count-notes-by-ticket-nr))


(comment
  
  (not= (ticket-operator :all-tickets)
        '())
  
  (not= (ticket-operator :get-ticket "TICKET-1")
        '())
  
  (= (ticket-operator :get-ticket "TICKET-")
     '())
  
  (not= (ticket-operator :notes-by-ticket-id 2)
        '())
  
  (not= (ticket-operator :count-notes-by-ticket-nr)
        '())
  
  )