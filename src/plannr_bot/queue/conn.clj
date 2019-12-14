(ns plannr-bot.queue.conn
  (:require [taoensso.carmine :as car :refer (wcar)]
            [config.core :refer [env]]))

(def redis-conn {:pool {} :spec {:host (:q_host env) :port (:port env) :password (:q_pass env)}})
(defmacro wcar* [& body] `(car/wcar redis-conn ~@body))

;(def listener
;  (car/with-new-pubsub-listener (:spec redis-conn)
;                                {"foobar" (fn f1 [msg] (println "Channel match: " msg))
;                                 "foo*"   (fn f2 [msg] (println "Pattern match: " msg))}
;                                (car/subscribe  "foobar" "foobaz")
;                                (car/psubscribe "foo*")))
