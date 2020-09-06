(ns ticket-system.frontend.view.open-ticket
  (:require
   [re-frame.core :as rf]
   [ticket-system.frontend.view.header :as header-view]
   [ticket-system.frontend.subscription.tickets :as ticket-evt]))

(defn- render-ticket
  []
  (let [ticket @(rf/subscribe [::ticket-evt/edit-ticket])]
    [:div (:ticket-beschreibung ticket)]))

(defn render
  []
  [:<>
   [header-view/render]
   [:div {:class "wrapper"}
    [:nav {:class "sidebar"}
     "Navbar"]
    [:main
     [render-ticket]]]])