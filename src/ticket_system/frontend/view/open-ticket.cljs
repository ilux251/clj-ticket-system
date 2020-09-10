(ns ticket-system.frontend.view.open-ticket
  (:require
   [re-frame.core :as rf]
   [ticket-system.frontend.view.header :as header-view]
   [ticket-system.frontend.subscription.tickets :as ticket-evt]
   [ticket-system.frontend.events.app :as app-evt]))

(defn- ticket-information
  [ticket]
  [:div {:class "ticket-information ticket-container"}
   [:h2 {:class "container-label"} "Information"]
   [:h2 {:class "ticket-bezeichnung"}
    (:ticket-bezeichnung ticket)]
   [:span {:class "ticket-datum"} (:last-update ticket)]
   [:div {:class "ticket-beschreibung"}
    (:ticket-beschreibung ticket)]
   [:div {:class "separator"}]
   [:h2 {:class "container-label"} "Notes"]])

(defn- ticket-todos
  []
  [:div {:class "ticket-tasks ticket-container"}
   [:h2 {:class "container-label"} "Task"]
   [:p "Aktuell wurden keine Aufgaben definiert."]])

(defn- render-ticket
  []
  (let [ticket @(rf/subscribe [::ticket-evt/edit-ticket])]
    [:div {:class "ticket-main-content"}
     [ticket-information ticket]
     [ticket-todos]]))

(defn render
  []
  [:<>
   [header-view/render]
   [:div {:class "wrapper"}
    [:nav {:class "sidebar"}
     "Navbar"]
    [:main {:class "open-ticket"}
     [:button {:class "back-button"
               :on-click #(rf/dispatch [::app-evt/change-view :ticket])} 
      "Back"]
     [render-ticket]]]])