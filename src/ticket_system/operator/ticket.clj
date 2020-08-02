(ns ticket-system.operator.ticket
  (:require [ticket-system.sql.ticket :as ticket]))

(defn- ticket-operation
  [op & _params]
  op)

(defmulti ticket-operator ticket-operation)

(defmethod ticket-operator :all-tickets
  [_]
  (ticket/all-tickets))

(defmethod ticket-operator :get-ticket
  [_ ticket-nr]
  (ticket/get-ticket ticket-nr))


(comment
  
  (not= (ticket-operator :all-tickets)
        '())
  
  (not= (ticket-operator :get-ticket "TICKET-1")
        '())
  
  (= (ticket-operator :get-ticket "TICKET-2")
     '())
  
  )