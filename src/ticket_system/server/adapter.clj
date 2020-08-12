(ns ticket-system.server.adapter
  (:require [ticket-system.server.operator.ticket :as operator]))

(defn adapter-handler
  [handler]
  (fn [request]
    (let [{:keys [op] :as response} (handler request)]
      (cond-> response
        op (-> (assoc :body (apply operator/ticket-operator op))
               (dissoc :op))))))