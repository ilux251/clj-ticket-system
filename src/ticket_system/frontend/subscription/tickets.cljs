(ns ticket-system.frontend.subscription.tickets
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::get-all-tickets
 (fn [db]
   (get-in db [:ticket :tickets])))