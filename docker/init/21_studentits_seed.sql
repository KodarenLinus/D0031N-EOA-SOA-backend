\connect studentits

INSERT INTO students (username, personnummer) VALUES
 ('sveedz-4','19940613-2345')
ON CONFLICT DO NOTHING;