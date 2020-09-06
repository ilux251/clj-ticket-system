(ns ticket-system.frontend.view.ticket
  (:require 
   [re-frame.core :as rf]
   [reagent.core :as r]
   [clojure.string :as cs]
   [ticket-system.frontend.util :refer [time-ago]]
   [ticket-system.frontend.subscription.tickets :as ticket-sub]
   [ticket-system.frontend.events.tickets :as ticket-evt]
   [ticket-system.frontend.view.header :as header-view]))

(defn- open-ticket-event
  [evt]
  (let [currentNode (-> evt
                        .-target)
        ticket-node (->> (iterate #(.-parentNode %) currentNode)
                         (reduce #(if (= (.-className %1) "ticket") (reduced %1) %2)))]
    (.preventDefault evt)
    (rf/dispatch [::ticket-evt/edit-ticket (.getAttribute ticket-node "ticketid")])))

(defn- status
  [status]
  (-> status
      (cs/upper-case)
      (cs/replace #"-" " ")))

(defn- ticket-template
  [ticket]
  (r/create-class
   {:component-did-mount 
    (fn [comp]
      (let [node (r/dom-node comp)]
        (.addEventListener node "dblclick" open-ticket-event)))
    
    :reagent-render
    (fn [ticket]
      [:div {:class "ticket"
             :ticketid (:id ticket)}
       [:div {:class "ticket-header"}
        [:div {:class "ticket-nr"} (:ticket-nr ticket)]
        [:div {:class "ticket-bezeichnung"} (:ticket-bezeichnung ticket)]]
       [:div {:class "ticket-content"}
        [:div {:class "ticket-beschreibung"} (:ticket-beschreibung ticket)]
        [:div {:class "ticket-footer"}
         [:div {:class "ticket-last-update"} (str "Last Update: " (time-ago (:last-update ticket)))]
         [:div {:class (str "ticket-status " (:ticket-status ticket))} (status (:ticket-status ticket))]]]])}))

(defn- render-tickets
  []
  (let [tickets @(rf/subscribe [::ticket-sub/get-all-tickets])]
    [:div {:class "tickets"}
     (for [ticket tickets]
       ^{:key (str "ticket " (:ticket-nr ticket))}
       [ticket-template ticket])]))

(defn render
  []
  [:<>
   [header-view/render]
   [:div {:class "wrapper"}
    [:nav {:class "sidebar"}
     "Navbar"]
    [:main
     [render-tickets]]]])