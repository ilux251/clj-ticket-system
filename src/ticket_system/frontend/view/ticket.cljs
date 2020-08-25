(ns ticket-system.frontend.view.ticket
  (:require 
   [re-frame.core :as rf]
   [reagent.core :as r]
   [cljs-time.core :as time]
   [cljs-time.format :as time-format]
   [clojure.string :as cs]
   [ticket-system.frontend.util :refer [toggle-class]]
   [ticket-system.frontend.subscription.tickets :as ticket-sub]
   [ticket-system.frontend.events.tickets :as ticket-evt]
   [ticket-system.frontend.view.header :as header-view]))

(defn- open-ticket-event
  [evt]
  (let [path (-> evt
                 .-path)
        ticket-node (-> (take-while #(not= (.-className %) "tickets") path)
                        last)]
    (.preventDefault evt)
    (rf/dispatch [::ticket-evt/edit-ticket (.getAttribute ticket-node "ticketid")])))

(defn- time-ago
  [date]
  (let [time-format (time-format/formatter "yyyyMMdd'T'HHmmss")
        unparse-date-format (time-format/formatter "dd'/'MM'/'yyyy HH':'mm")
        parsed-date (time-format/parse time-format date)
        current-timezone-date (time/minus parsed-date (time/hours 2))
        unparsed-date (time-format/unparse unparse-date-format parsed-date)
        time-interval (time/interval current-timezone-date (time/time-now))
        in-minutes (time/in-minutes time-interval)
        in-hours (time/in-hours time-interval)]
    (cond
      (> in-hours 24) unparsed-date
      
      (>= in-hours 1) (str in-hours " hrs ago")
      
      (>= in-minutes 1) (str in-minutes " min ago")
      
      :just-few-seconds-ago (str "just now"))))

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