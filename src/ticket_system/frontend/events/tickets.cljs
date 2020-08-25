(ns ticket-system.frontend.events.tickets
  (:require
   [day8.re-frame.http-fx]
   [re-frame.core :as rf]
   [ajax.core :refer [json-response-format]]
   [ticket-system.frontend.events.app :as app-events]))

(rf/reg-event-db
 :success-response
 (fn [db [_ result]]
   (assoc-in db [:ticket :tickets] result)))

(rf/reg-event-db
 :error-response
 (fn [db [_ result]]
   (assoc db :last-error result)))

(rf/reg-event-db
 ::edit-ticket
 (fn [db [_ ticket-id]]
   (let [tickets (get-in db [:ticket :tickets])
         ticket (-> (filter #(= (str (:id %)) ticket-id) tickets)
                    first)]
     (rf/dispatch [::app-events/change-view :open-ticket])
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
 (fn [_ _]
   {:http-xhrio {:method :GET
                 :uri "http://localhost:5000/ticket/all"
                 :response-format (json-response-format {:keywords? true})
                 :on-success [:success-response]
                 :on-failure [:error-response]}}))


(comment
  
  ;; Playground
  
  (def people [{:id 1 :name "Alexander"} {:id 2 :name "Ritsch"} {:id 3 :name "Maik"}])
  
  (for [p people
        :let [find-p (= (:id p) 1)]]
    (if find-p
      (assoc p :name "Alex")
      p))
  
  (def list1 '(1 2 3))
  (def list2 '(4 5 6))
  
  (defn zip
    [& colls]
    (apply map vector colls))
  
  (zip list1 list2)
  
  (mapv vector list1 list2)
  )