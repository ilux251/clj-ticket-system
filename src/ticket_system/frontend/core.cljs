(ns ticket-system.frontend.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [goog.dom :as dom]
            [ticket-system.frontend.panel :refer [panel]]
            [ticket-system.frontend.events.tickets :as ticket-events]
            [ticket-system.frontend.events.app :as app-events]))

(defn- get-app-element
  []
  (dom/getElement "app"))

(defn- mount-app
  []
  (rf/clear-subscription-cache!)
  (r/render-component [panel] (get-app-element)))

(defn ^:after-load onload
  []
  (mount-app))

(defonce init
  (do
    (rf/dispatch-sync [::ticket-events/get-all-tickets])
    (rf/dispatch-sync [::app-events/init-db])
    (mount-app)))