(ns ticket-system.frontend.events.tickets
  (:require
   [day8.re-frame.http-fx]
   [re-frame.core :as rf]
   [ajax.core :refer [json-response-format]]))

(rf/reg-event-db
 :success-response
 (fn [db [_ result]]
   (assoc-in db [:ticket :tickets] result)))

(rf/reg-event-db
 :error-response
 (fn [db [_ result]]
   (assoc db :last-error result)))

(rf/reg-event-db
 ::change-ticket
 (fn [db _]
   (assoc-in db [:ticket :tickets] [{:ticket-nr 1 :ticket-bezeichnung "Changed Ticket List"}])))

(rf/reg-event-fx
 ::get-all-tickets
 (fn [db _]
   {:http-xhrio {:method :GET
                 :uri "http://localhost:5000/ticket/all"
                 :response-format (json-response-format {:keywords? true})
                 :on-success [:success-response]
                 :on-failure [:error-response]}
    :db (assoc-in db [:ticket :loading?] true)}))