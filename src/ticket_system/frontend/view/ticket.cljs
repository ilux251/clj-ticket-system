(ns ticket-system.frontend.view.ticket
  (:require 
   [re-frame.core :as rf]
   [ticket-system.frontend.subscription.tickets :as ticket-sub]
   [ticket-system.frontend.events.tickets :as ticket-evt]
   [ticket-system.frontend.view.header :as header-view]))

(defn- ticket
  [tickets]
  (for [ticket tickets]
    ^{:key (:ticket-nr ticket)}
    [:div (:ticket-bezeichnung ticket)]))

(defn- render-tickets
  []
  (let [tickets @(rf/subscribe [::ticket-sub/get-all-tickets])]
    [:div (ticket tickets)]))

(defn render
  []
  [:div
   [header-view/render]
   [:main
    [:div "Ticket-Content"]
    [render-tickets]
    [:button {:on-click #(rf/dispatch [::ticket-evt/change-ticket])
              :class "change-button"} "Change ticket list"]]])