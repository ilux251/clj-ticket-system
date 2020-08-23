(ns user
  (:require [figwheel.main.api :as fig]))

(defn- fig-start
  []
  (fig/start "dev"))

(defn- fig-stop
  []
  (fig/stop "dev"))

(defn- fig-restart
  []
  (fig-stop)
  (fig-start))