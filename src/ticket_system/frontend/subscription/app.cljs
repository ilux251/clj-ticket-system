(ns ticket-system.frontend.subscription.app
  (:require 
   [re-frame.core :as rf]))

(rf/reg-sub
 ::current-view
 (fn [db]
   (get db :view)))

(rf/reg-sub
 ::app-state
 (fn [db]
   db))

(rf/reg-sub
 ::popup
 (fn [db]
   (get db :popup)))