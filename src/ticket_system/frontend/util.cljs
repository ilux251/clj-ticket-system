(ns ticket-system.frontend.util)

(defn toggle-class
  [node toggle-class]
  (let [class-list (.-classList node)]
    (if (.contains class-list toggle-class)
      (.remove class-list toggle-class)
      (.add class-list toggle-class))))