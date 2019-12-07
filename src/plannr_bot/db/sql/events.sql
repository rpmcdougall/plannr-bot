-- :name events
-- :command :query
-- :result n
-- :doc select all events
SELECT *
  FROM plannr_event


-- A :result value of :n below will return affected row count:
-- :name insert-event :insert :raw
-- :doc Inserts a single event
insert into plannr_event (event_id, event_name, host, attendees, event_time)
values (:event_id, :event_name, :host, :attendees, :event_time)
