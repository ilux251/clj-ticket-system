(ns ticket-system.frontend.template.popup
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [ticket-system.frontend.subscription.app :as sub]
   [ticket-system.frontend.events.app :as evt]
   [quill :as quill]))

(defn popup
  [name]
  name)

(defmulti create-popup popup)

(defmethod create-popup :create-ticket
  []
  (r/create-class
   {:component-did-mount 
    (fn [_comp]
      (let [example-text (str "That a rich text editor")]
        (-> (new js/Quill "#editor" (clj->js {:modules {:toolbar "#toolbar"}
                                              :theme "snow"}))
            (.setText example-text))))
    
    :reagent-render
    (fn []
       [:div
        [:input {:type "text"
                 :placeholder "Titel"}]
        [:div#toolbar
         [:button {:class "ql-bold"} "Bold"]
         [:button {:class "ql-italic"} "Italic"]]
        [:div#editor]])}))

(defn render-popup
  []
  (let [popup @(rf/subscribe [::sub/popup])]
    (when (some? popup)
      [:div {:class "popup"}
       [:div {:class "header"}
        [:button {:on-click #(rf/dispatch [::evt/set-popup nil])} "Close"]]
       [create-popup popup]])
    ))