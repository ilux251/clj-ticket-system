(ns ticket-system.frontend.subscription.header
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::global-search
 (fn [db]
   (get db :global-search)))