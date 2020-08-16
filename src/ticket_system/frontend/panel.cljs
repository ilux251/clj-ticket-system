(ns ticket-system.frontend.panel
  (:require [ticket-system.frontend.view.ticket :as ticket-panel]))

(defn- operation
  [panel]
  panel)

(defmulti panel-operation operation)

(defmethod panel-operation :ticket
  []
  (ticket-panel/render))

(defn panel 
  []
  (panel-operation :ticket))