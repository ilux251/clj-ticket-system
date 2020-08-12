(ns ticket-system.server.sql.util)

(def db 
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname "resources/ticket-system.db"})