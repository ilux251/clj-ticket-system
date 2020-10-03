(ns ticket-system.frontend.events.utils
  (:require [re-frame.core :as rf]
            [ajax.core :refer [json-response-format]]))

(rf/reg-event-db
 :success-response
 (fn [db [_ path result]]
   (assoc-in db path result)))

(rf/reg-event-db
 :error-response
 (fn [db [_ result]]
   (assoc db :last-error result)))

(defn request-function
  [url-path response-path & params]
  {:http-xhrio {:method :GET
                :uri (str "http://localhost:5000/" url-path)
                :response-format (json-response-format {:keywords? true})
                :params params
                :content-type :json
                :headers {"Accept" "application/json" :content-type "application/json;charset=utf-8"}
                :on-success [:success-response response-path]
                :on-failure [:error-response]}})