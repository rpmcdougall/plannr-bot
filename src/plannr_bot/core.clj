(ns plannr-bot.core
  (:gen-class)
  (:require [discord.bot :as bot]
            [plannr_bot.db.conn :refer [db]]
            [plannr_bot.db.sql :as sql]))

(defn -main
  ;;"TODO: Implement Interaction with watch service from libs"
  [& args]
  (bot/start))
