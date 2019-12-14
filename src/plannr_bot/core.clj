(ns plannr-bot.core
  (:gen-class)
  (:require [discord.bot :as bot]
            [plannr-bot.handler.messages :as event-handler]
            [clj-time.coerce :as c]
            [discord.utils :as utils]
            [discord.embeds :as embeds]))

(defn -main
  ;;"TODO: Implement Interaction with watch service from libs"
  [& args]
  ;; TODO: Implement listener as future here
  ;; (future (listener))
  (bot/start))

(bot/defcommand list-events
  [client message]
  "Lists available events. !list-events"
  (def events (event-handler/fetch-events))
  (bot/pm (-> (embeds/create-embed :title "Events"
                                   :color (utils/rgb->integer 0 255 255))
              (embeds/+field "Events" events))))

(bot/defcommand plan-event
  [client message]
  "Creates a new event. !plan-event event-name | event-time"
  (def event-created (event-handler/publish-event (event-handler/parse-event message)))
  (bot/pm (format "Your event %s has been been created and will occur at %s. Have fun!"
                  (:event_name event-created) (:event_time event-created))))
(bot/defcommand join-event
  [client message]
  "Allows user to join an event. !join-event event-name"
  (def update-payload (event-handler/parse-join message))
  (event-handler/update-attendees update-payload)
  (def updated-attendees (event-handler/fetch-event (:event_name update-payload)))
  (bot/pm (format "You have successfully joined the event %s. The event starts at %s. Have Fun!" (:event_name updated-attendees) (:event_time updated-attendees))))

(bot/defcommand cancel-event
  [client message]
  "Cancels and existing event. !cancel-event event-name"
  (event-handler/cancel-event (event-handler/parse-basic message))
  (bot/pm (format "Your event %s has been cancelled." (:content message))))

(bot/defcommand leave-event
  [client message]
  "Allows an attendee to leave an event they joined. !leave-event event-name"
  (event-handler/remove-attendee (event-handler/parse-leave-event message))
  (bot/pm (format "You have successully left the event %s." (:content message))))

(bot/defcommand roster
  [client message]
  "Allows a user to list the attendees for a given event !roster event-name"
  (def event-roster (event-handler/fetch-attendees (event-handler/parse-basic message)))
  (clojure.pprint/pprint event-roster)
  (bot/pm (-> (embeds/create-embed :title (:content message)
                                   :color (utils/rgb->integer 0 255 255))
              (embeds/+field "Roster" event-roster))))

