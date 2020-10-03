(ns ticket-system.frontend.view.open-ticket
  (:require
   [re-frame.core :as rf]
   [ticket-system.frontend.view.header :as header-view]
   [ticket-system.frontend.subscription.tickets :as ticket-sub]
   [ticket-system.frontend.events.app :as app-evt]
   [ticket-system.frontend.util :as util]))

(defn- render-note
  [note]
  [:div {:class "note"}
   [:div {:class "content"} (:content note)]
   [:div {:class "date"}
    [:div (util/time-ago (:date note))]]])

(defn- render-note-section
  []
  (let [notes @(rf/subscribe [::ticket-sub/notes-by-ticket])]
    [:div {:class "ticket-notes"}
     (if (not= (count notes) 0)
       (for [note notes]
         ^{:key (str "note-" (:id note))}
         [render-note note])

       [:div "Aktuell gibt es keine Notizen"])]))

(defn- ticket-information
  [ticket]
  [:div {:class "ticket-information ticket-container"}
   [:h2 {:class "container-label"} "Information"]
   [:h2 {:class "ticket-bezeichnung"}
    (:ticket-bezeichnung ticket)]
   [:span {:class "ticket-datum"} (util/time-ago (:last-update ticket))]
   [:div {:class "ticket-beschreibung"}
    (:ticket-beschreibung ticket)]
   [:h2 {:class "container-label"} "Notes"]
   [render-note-section]])

(defn- ticket-todos
  []
  [:div {:class "ticket-tasks ticket-container"}
   [:h2 {:class "container-label"} "Task"]
   [:p "Aktuell wurden keine Aufgaben definiert."]])

(defn- render-ticket
  []
  (let [ticket @(rf/subscribe [::ticket-sub/edit-ticket])]
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