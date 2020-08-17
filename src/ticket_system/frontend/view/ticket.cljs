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
    [:div {:class "ticket"}
     [:div {:class "ticket-nr"} (:ticket-nr ticket)]
     [:div {:class "ticket-bezeichnung"} (:ticket-bezeichnung ticket)]
     [:div {:class "ticket-beschreibung"} (:ticket-beschreibung ticket)]]))

(defn- render-tickets
  []
  (let [tickets @(rf/subscribe [::ticket-sub/get-all-tickets])]
    [:div {:class "tickets"}
     (ticket tickets)]))

(defn render
  []
  [:<>
   [header-view/render]
   [:div {:class "wrapper"}
    [:nav {:class "sidebar"}
     "Navbar"]
    [:main
     [render-tickets]
     [:button {:on-click #(rf/dispatch [::ticket-evt/change-ticket])
               :class "change-button"} "Change ticket list"]]]])