(ns ticket-system.frontend.view.header
  (:require [re-frame.core :as rf]
            [ticket-system.frontend.events.header :as header-evts]
            [ticket-system.frontend.subscription.header :as header-sub]))

(defn- sub-global-search
  []
  (let [search-value (rf/subscribe [::header-sub/global-search])]
    [:div @search-value]))

(defn render
  []
  [:header
   [:div {:class "logo-container"}
    [:img {:src "img/logo.png"
           :alt "Logo"}]]
   [:div {:class "searchfield fa"}
    [:input {:type :text
             :placeholder "Nach Tickets suchen"
             :on-change #(rf/dispatch [::header-evts/set-global-search (-> % .-target .-value)])}]
    [sub-global-search]]
   [:button {:class "create-button button-with-icon"}
    [:i {:class "fas fa-plus"}]
    [:span "New Ticket"]]])