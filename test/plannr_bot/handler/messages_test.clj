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


(deftest trim-outer-test
  "Test outer trim utility function"
  (is (= "test test" (plannr-bot.handler.messages/trim-outer " test test "))))
