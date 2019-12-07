(ns plannr_bot.db.conn
  (:require [config.core :refer [env]]))


(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname (:db_conn env)
   :user (:db_user env)
   :password (:db_pass env)
   })