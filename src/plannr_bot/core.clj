(ns plannr-bot.core
  (:gen-class)
  (:require [discord.bot :as bot]))


(defn -main
  ;;"TODO: Implement Interaction with watch service from libs"
  [& args]
  ;; TODO: Implement listener as future here
  ;; (future (listener))
  (bot/start))

