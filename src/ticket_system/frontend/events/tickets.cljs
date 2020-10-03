(ns ticket-system.frontend.events.tickets
  (:require
   [day8.re-frame.http-fx]
   [re-frame.core :as rf]
   [ticket-system.frontend.events.app :as app-events]
   [ticket-system.frontend.events.utils :as util]))

(rf/reg-event-db
 ::edit-ticket
 (fn [db [_ ticket-id]]
   (let [tickets (get-in db [:ticket :tickets])
         ticket (-> (filter #(= (str (:id %)) ticket-id) tickets)
                    first)]
     (rf/dispatch [::app-events/change-view :open-ticket])
     (rf/dispatch [::get-notes-by-ticket-id ticket-id])
     (assoc-in db [:ticket :edit-ticket] ticket))))

(rf/reg-event-db
 ::change-ticket-status-of-first
 (fn [db _]
   (let [tickets (get-in db [:ticket :tickets])
         first-ticket (first tickets)
         changed-ticket (assoc first-ticket :ticket-status "todo")
         rest-tickets (rest tickets)
         edit-tickets (conj rest-tickets changed-ticket)]
     (assoc-in db [:ticket :tickets] edit-tickets))))

(rf/reg-event-fx
 ::get-all-tickets
 (fn [_ _] (util/request-function "ticket/all" [:ticket :tickets])))

(rf/reg-event-fx
 ::get-notes-by-ticket-id
 (fn [_ [_ ticket-id]]
   (util/request-function "ticket/notesByTicketId" [:ticket :current-notes] {:ticket-id ticket-id})))

(comment
  
  ;; Playground
  
  (def people [{:id 1 :name "Alexander"} {:id 2 :name "Ritsch"} {:id 3 :name "Maik"}])
  
  (for [p people
        :let [find-p (= (:id p) 1)]]
    (if find-p
      (assoc p :name "Alex")
      p))
  
  (map #(if (= 2 (:id %))
          (assoc % :name "Ritsch Ulrich")
          %) people)
  
  (def list1 '(1 2 3))
  (def list2 '(4 5 6))
  
  (defn zip
    [& colls]
    (apply map vector colls))
  
  (zip list1 list2)
  
  (mapv vector list1 list2)
  )