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

(defn mk-map-string-event
  "Format event into string"
  [event]
  (format "%s @ %s" (:event_name event) (:event_time event)))

(defn handle-lazy-seq-output-event
  "Turns a sequence into a newline delimeted string of events"
  [events]
  (clojure.string/join "\n" (map mk-map-string-event events)))

(defn handle-lazy-seq-output-roster
  "Turns a sequence into a newline delimeted string of events"
  [roster]
  (clojure.string/join "\n" (:attendees (nth roster 0))))

(defn parse-event
  "Handles parsing from discord message data into an event object."
  [message-object]
  (let [split-event (str/split (:content message-object) #"\|")
        user (:username (:author message-object))
        event-name (trim-outer (get split-event 0))
        event-time (trim-outer (get split-event 1))
        event-object {:host user :event_name event-name :event_time event-time}]
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

(defn parse-basic
  "Handles parsing from discord message for event name only messages."
  [message-object]
  (let [event-object {:event_name (trim-outer (:content message-object))}]
    event-object))

(defn cancel-event
  "Removes and event from the table"
  [event-object]
  (sql/delete-event-by-event-name db event-object))

(defn update-attendees
  "Publishes update to attendees list"
  [event-object]
  (sql/update-event-attendees db event-object))

(defn fetch-event
  "Fetches a specified event."
  [event-name]
  (update (sql/fetch-event-by-name db {:event_name event-name}) :event_time c/to-string))

(defn fetch-events
  "Fetches all events"
  []
  (def result (sql/events-listing db))
  (def parsed (handle-lazy-seq-output-event result))
  parsed)

(defn parse-leave-event
  "Handles parsing a discord message object for leave event."
  [message-object]
  (let [event-object {:event_name (:content message-object)
                      :attendee (:username (:author message-object))}]
    event-object))

(defn remove-attendee
  [event-object]
  "Removes an attendee from an event."
  (sql/delete-attendee-by-event-name db event-object))

(defn fetch-attendees
  [event-object]
  "Retrieves list of attendees for a given event."
  (def attendees (handle-lazy-seq-output-roster (sql/fetch-attendees-by-event db event-object)))
  attendees)
