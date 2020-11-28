(ns ticket-system.frontend.panel
  (:require 
   [re-frame.core :as rf]
   [ticket-system.frontend.view.ticket :as ticket-panel]
   [ticket-system.frontend.view.open-ticket :as open-ticket-panel]
   [ticket-system.frontend.subscription.app :as app-sub]
   [ticket-system.frontend.template.popup :as popup]))

(defn- operation
  [panel]
  panel)

(defmulti panel-operation operation)

(defmethod panel-operation :ticket
  []
  (ticket-panel/render))

(defmethod panel-operation :open-ticket
  []
  (open-ticket-panel/render))

(defmethod panel-operation nil
  []
  [:div {:class "error-screen"}
   "Can't render view"])

(defn- render-background
  []
  (let [popup @(rf/subscribe [::app-sub/popup])]
    [:div
     (when (not (nil? popup))
       [:div {:class "background"}])]))

(defn panel
  []
  (let [view @(rf/subscribe [::app-sub/current-view])
        app-state @(rf/subscribe [::app-sub/app-state])]
    [:<>
     (panel-operation view)
     (popup/render-popup)
     [render-background]]))