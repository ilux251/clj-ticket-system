(ns ticket-system.frontend.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [goog.dom :as dom]
            [ticket-system.frontend.panel :refer [panel]]
            [ticket-system.frontend.events.tickets :as ticket]))

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
  (do (mount-app)
      (rf/dispatch [::ticket/get-all-tickets])))