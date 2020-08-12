(ns ticket-system.frontend.panel)

(defn- header
  []
  [:header
   [:h1 "Ticket-System Application"]])

(defn- content
  []
  [:main
   [:h2 "Main Content"]])

(defn panel 
  []
  [:<>
   [header]
   [content]])