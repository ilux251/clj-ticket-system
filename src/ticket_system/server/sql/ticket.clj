(ns ticket-system.server.sql.ticket
  (:require [clojure.java.jdbc :as j]
            [ticket-system.server.sql.util :as db-util]))

(defn all-tickets
  []
  (j/query db-util/db (str "select * from Ticket")))

(defn ticket-by-ticket-nr
  [ticket-nr]
  (j/query db-util/db (str "select * from Ticket where `ticket-nr` = '" ticket-nr "'")))

(defn notes-by-ticket-id
  [ticket-id]
  (j/query db-util/db (str "select note.`id`, `content`, `date` from note join ticket t on t.`note-id` = note.`note-id` where t.id = " ticket-id)))

(defn count-notes-by-ticket-nr
  []
  (j/query db-util/db (str "select t.`ticket-nr`, count(*) as notes from Ticket t join Note n on n.`note-id` = t.`note-id` group by t.`ticket-nr`")))