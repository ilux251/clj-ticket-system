(ns ticket-system.frontend.util
  (:require
   [cljs-time.core :as time]
   [cljs-time.format :as time-format]))

(defn toggle-class
  [node toggle-class]
  (let [class-list (.-classList node)]
    (if (.contains class-list toggle-class)
      (.remove class-list toggle-class)
      (.add class-list toggle-class))))

(defn time-ago
  [date]
  (let [time-format (time-format/formatter "yyyyMMdd'T'HHmmss")
        unparse-date-format (time-format/formatter "dd'/'MM'/'yyyy HH':'mm")
        parsed-date (time-format/parse time-format date)
        current-timezone-date (time/minus parsed-date (time/hours 2))
        unparsed-date (time-format/unparse unparse-date-format parsed-date)
        time-interval (time/interval current-timezone-date (time/time-now))
        in-minutes (time/in-minutes time-interval)
        in-hours (time/in-hours time-interval)]
    (cond
      (> in-hours 24) unparsed-date

      (>= in-hours 1) (str in-hours " hrs ago")

      (>= in-minutes 1) (str in-minutes " min ago")

      :just-few-seconds-ago (str "just now"))))