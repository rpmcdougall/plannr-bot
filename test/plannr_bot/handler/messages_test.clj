(ns plannr-bot.handler.messages-test
  (:require [clojure.test :refer :all])
  (:require [plannr-bot.handler.messages :refer [parse-event]]))

(deftest parse-event-test-basic
  "Test parsing of a simple conformant discord message text to event-object"
  (def result (parse-event {:content "funtimes|12/10/2019 05:00" :author {:username "leetgamer1337"}}))
  (is (= (:host result) "leetgamer1337"))
  (is (= (:event_name result) "funtimes"))
  (is (= (:event_time result) "12/10/2019 05:00"))
  )

(deftest parse-event-test-inner-whitespace
  "Test parsing of an event with inner whitespace to event-object"
  (def result (parse-event {:content "Molten Core Raid Team Group A|12/10/2019 05:00" :author {:username "leetgamer1337"}}))
  (is (= (:host result) "leetgamer1337"))
  (is (= (:event_name result) "Molten Core Raid Team Group A"))
  (is (= (:event_time result) "12/10/2019 05:00"))
  )

(deftest parse-event-test-outer-whitespace
  "Test parsing of an event with inner whitespace to event-object"
  (def result (parse-event {:content " Extemely Important Event       |  12/10/2019 05:00    " :author {:username "leetgamer1337"}}))
  (is (= (:host result) "leetgamer1337"))
  (is (= (:event_name result) "Extemely Important Event"))
  (is (= (:event_time result) "12/10/2019 05:00"))
  )

(deftest parse-join
  "Test parsing of the join command"
  (def result (plannr-bot.handler.messages/parse-join {:content "Test Event" :author {:username "leetgamer1337"}}))
  (is (= (:event_name result) "Test Event"))
  (is (= (:attendee result)  ["leetgamer1337"]))
  )

(deftest mk-map-string
  "Tests parsing of event listing into string output"
  (def result (plannr-bot.handler.messages/mk-map-string {:event_name "Smoked BBQ Pit Extravaganza"
                                                          :event_time "2019-12-10T07:00:00.000Z"}))
  (is (= result "Smoked BBQ Pit Extravaganza @ 2019-12-10T07:00:00.000Z")))

(deftest handle-seq-output
  "Tests parsing of event listing into string output"
  (def result (plannr-bot.handler.messages/handle-seq-output [{:event_name "Smoked BBQ Pit Extravaganza"
                :event_time "2019-12-10T07:00:00.000Z"} {:event_name "Smoked BBQ Pit Extravaganza"
                                                         :event_time "2019-12-10T07:00:00.000Z"}]) )
  (is (= result "Smoked BBQ Pit Extravaganza @ 2019-12-10T07:00:00.000Z\nSmoked BBQ Pit Extravaganza @ 2019-12-10T07:00:00.000Z")))

(deftest trim-outer-test
  "Test outer trim utility function"
  (is (= "test test" (plannr-bot.handler.messages/trim-outer " test test "))))
