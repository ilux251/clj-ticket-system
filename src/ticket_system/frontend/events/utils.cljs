(ns ticket-system.frontend.events.utils
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
 ::success-response
 (fn [db [_ result path]]))