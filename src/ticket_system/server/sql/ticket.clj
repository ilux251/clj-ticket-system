(ns ticket-system.server.sql.ticket
  (:require [clojure.java.jdbc :as j]
            [ticket-system.server.sql.util :as db-util]))

(defn all-tickets
  []
  (j/query db-util/db (str "select * from Ticket")))

(defn get-ticket
  [ticket-nr]
  (j/query db-util/db (str "select * from Ticket where `ticket-nr` = '" ticket-nr "'")))