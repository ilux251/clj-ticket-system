(ns ticket-system.sql.ticket
  (:require [clojure.java.jdbc :as j]
            [ticket-system.sql.util :as db-util]))

(defn all-tickets
  []
  (j/query db-util/db (str "select * from Ticket")))

(defn get-ticket
  [ticket-nr]
  (j/query db-util/db (str "select * from Ticket where `ticket-nr` = '" ticket-nr "'")))