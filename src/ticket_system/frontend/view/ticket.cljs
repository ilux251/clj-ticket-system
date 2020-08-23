(ns ticket-system.frontend.view.ticket
  (:require 
   [re-frame.core :as rf]
   [reagent.core :as r]
   [cljs-time.core :as time]
   [cljs-time.coerce :as time-c]
   [cljs-time.format :as time-format]
   [ticket-system.frontend.subscription.tickets :as ticket-sub]
   [ticket-system.frontend.events.tickets :as ticket-evt]
   [ticket-system.frontend.view.header :as header-view]))

(defn- change-option-mode
  [evt]
  (let [node (r/dom-node (.-target evt))
        parent (->> (iterate (fn [node] (.log js/console node)) node)
                    (take 1))]
    (.preventDefault evt)
    (.log js/console parent)))

(defn- time-ago
  [date]
  (let [time-format (time-format/formatter "yyyyMMdd'T'HHmmss")
        parsed-date (time-format/parse time-format date)
        current-timezone-date (time/minus parsed-date (time/hours 2))
        time-interval (time/interval current-timezone-date (time/time-now))
        in-minutes (time/in-minutes time-interval)
        in-hours (time/in-hours time-interval)]
    (cond
      (> in-hours 24) current-timezone-date
      
      (>= in-hours 1) (str in-hours " hrs ago")
      
      (>= in-minutes 1) (str in-minutes " min ago")
      
      :just-few-seconds-ago (str "just now"))))

(defn- ticket-template
  [ticket]
  (r/create-class
   {:component-did-mount 
    (fn [comp]
      (let [node (r/dom-node comp)]
        (.addEventListener node "contextmenu" change-option-mode)))
    
    :reagent-render
    (fn [ticket]
      [:div {:class "ticket"}
       [:div {:class "ticket-header"}
        [:div {:class "ticket-nr"} (:ticket-nr ticket)]
        [:div {:class "ticket-bezeichnung"} (:ticket-bezeichnung ticket)]]
       [:div {:class "ticket-content"}
        [:div {:class "ticket-beschreibung"} (:ticket-beschreibung ticket)]
        [:div {:class "ticket-footer"}
         [:div {:class "ticket-last-update"} (str "Last Update: " (time-ago (:last-update ticket)))]
         [:div {:class (str "ticket-status " (:ticket-status ticket))} (:ticket-status ticket)]]]])}))

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
     [render-tickets]
     [:button {:on-click #(rf/dispatch [::ticket-evt/change-ticket])
               :class "change-button"} "Change ticket list"]]]])

(+ 1 1)

(comment
  (time-ago (time/date-time 2020 8 22 1 4 10)))