(ns ticket-system.frontend.template.popup
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [ticket-system.frontend.subscription.app :as sub]
   [ticket-system.frontend.events.app :as evt]
   [quill :as quill]))

(defn- input
  [name key & placeholder]
  [:input {:type "text"
           :id name
           :placeholder placeholder
           :on-change #(rf/dispatch [::evt/set-popup-values name key (-> % .-target .-value)])}])

(defn popup
  [name]
  name)

(defmulti create-popup popup)

(defmethod create-popup :create-ticket
  [name]
  (r/create-class
   {:component-did-mount 
    (fn [_comp]
      (let [popup-values @(rf/subscribe [::sub/popup-values name])
            quill-editor (new js/Quill "#editor" (clj->js {:modules {:toolbar "#toolbar"}
                                                           :theme "snow"}))
            quill-textbox (-> (.getElementsByClassName js/document "ql-editor")
                              first)
            quill-set-textbox-value (set! (.-innerHTML quill-textbox) (get popup-values "beschreibung" ""))]
        (.on quill-editor "text-change" #(rf/dispatch [::evt/set-popup-values "beschreibung" name (-> quill-textbox (.-innerHTML))]))))
    
    :reagent-render
    (fn [name]
      [:div
       [input "title" name "Ticket Bezeichnung"]
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