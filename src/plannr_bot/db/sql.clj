(ns plannr_bot.db.sql
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "plannr_bot/db/sql/events.sql")