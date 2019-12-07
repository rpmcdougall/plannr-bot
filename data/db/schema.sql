CREATE TABLE plannr_event(
                             id serial PRIMARY KEY,
                             event_id VARCHAR (120) UNIQUE NOT NULL,
                             event_name VARCHAR (50) UNIQUE NOT NULL,
                             host VARCHAR (50) NOT NULL,
                             attendees  VARCHAR(50) ARRAY,
                             event_time TIMESTAMP
);