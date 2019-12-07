(ns plannr-bot.handler.messages
  (:require [clojure.string :as str]
            [plannr_bot.db.conn :refer [db]]
            [plannr_bot.db.sql :as sql]
            [clj-time.coerce :as c]
            [clj-time.format :as f]
            [clj-time.jdbc])
  (:import (java.util UUID)))

(def trim-outer
  "Trims outer whitepsace from a string."
  (comp str/trimr str/triml))

(defn parse-event
  "Handles parsing from discord message data into an event object."
  [message-object]
 (let [split-event (str/split (:content message-object) #"\|")
       user (:username (:author message-object))
       event-name (trim-outer (get split-event 0))
       event-time (trim-outer (get split-event 1))
       event-object {:host user :event_name event-name :event_time event-time }]
   event-object))


(defn publish-event
  [event-object]
  "Receives and event object and publishes it to the database"
  (let [new-event {:event_id   (str (UUID/randomUUID))
                   :event_name (:event_name event-object)
                   :host       (:host event-object)
                   :attendees  (vector)
                   :event_time  (f/parse (f/formatter "MM/dd/YYYY HH:mm") (:event_time event-object))}]
    (update (sql/insert-event db new-event) :event_time c/to-string)))


(defn parse-join
  "Handles parsing from discord message for join event"
  [message-object]
  (let [event-object {:event_name (trim-outer (:content message-object))
                      :attendee [(:username (:author message-object))]}]
    event-object))

(defn update-attendees
  "Publishes update to attendees list"
  [event-object]
  (sql/update-event-attendees db event-object))

(defn fetch-event
  "Fetches a specified event."
  [event-name]
  (update (sql/fetch-event-by-name db {:event_name event-name}) :event_time c/to-string))

