(ns ticket-system.frontend.events.app
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
 ::init-db
 (fn [_ _]
   {:view :ticket}))

(rf/reg-event-db
 ::change-view
 (fn [db [_ view]]
   (assoc db :view view)))

(rf/reg-event-db
 ::set-popup
 (fn [db [_ popup]]
   (assoc db :popup popup)))

(rf/reg-event-db
 ::set-popup-values
 (fn [db [_ key popup-name value]]
   (assoc-in db [:popup-values popup-name key] value)))