-- :name events
-- :command :query
-- :result n
-- :doc select all events
SELECT *
  FROM plannr_event


-- A ":result" value of ":*" specifies a vector of records
-- (as hashmaps) will be returned
-- :name events-listing :? :*
-- :doc Get all events
SELECT event_name, event_time
  FROM plannr_event


-- A :result value of :n below will return affected row count:
-- :name insert-event :insert :raw
-- :doc Inserts a single event
insert into plannr_event (event_id, event_name, host, attendees, event_time)
values (:event_id, :event_name, :host, :attendees, :event_time)


-- :name update-event-attendees :! :n
-- :doc Adds additional attendees to an event
update plannr_event
set attendees = array_cat(attendees, :attendee)
where event_name = :event_name


-- A ":result" value of ":1" specifies a single record
-- (as a hashmap) will be returned
-- :name fetch-event-by-name :? :1
-- :doc Gets a single event by name
SELECT *
  FROM plannr_event
  WHERE event_name = :event_name


-- :name delete-event-by-event-name :! :n
delete from plannr_event where event_name = :event_name