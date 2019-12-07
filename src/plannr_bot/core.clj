(ns plannr-bot.core
  (:gen-class)
  (:require [discord.bot :as bot]
            [plannr-bot.handler.messages :as event-handler]
            [clj-time.coerce :as c]))


(defn -main
  ;;"TODO: Implement Interaction with watch service from libs"
  [& args]
  ;; TODO: Implement listener as future here
  ;; (future (listener))
  (bot/start))


(bot/defcommand plan-event
                [client message]
                "Creates a new event from user message."
                (def event-created (event-handler/publish-event (event-handler/parse-event message)) )
                (bot/pm (format "Your event %s has been been created and will occur at %s. Have fun!"
                                 (:event_name event-created) (:event_time event-created))))
(bot/defcommand join-event
                [client message]
                "Allows user to join an event."
                (def update-payload (event-handler/parse-join message))
                (event-handler/update-attendees update-payload)
                (def updated-attendees (event-handler/fetch-event (:event_name update-payload)))
                (bot/pm (format "You have successfully joined the event %s. The event starts at %s. Have Fun!" (:event_name updated-attendees) (:event_time updated-attendees))))