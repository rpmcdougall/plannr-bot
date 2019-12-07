(ns plannr-bot.handler.messages-test
  (:require [clojure.test :refer :all])
  (:require [plannr-bot.handler.messages :refer [parse-event]]))

(deftest parse-event-test
  "Test parsing of discord message text to event-object"
  (def result (parse-event {:content "funtimes 12/10/2019 05:00" :author {:username "leetgamer1337"}}))
  (is (= (:host result) "leetgamer1337"))
  (is (= (:event_name result) "funtimes"))
  (is (= (:event_time result) "12/10/2019 05:00"))
  )
