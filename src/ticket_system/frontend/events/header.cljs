(ns ticket-system.frontend.events.header
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
 ::set-global-search
 (fn [db [_ value]]
   (assoc db :global-search value)))