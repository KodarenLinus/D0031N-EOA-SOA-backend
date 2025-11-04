\connect studentits

CREATE TABLE IF NOT EXISTS students (
  id SERIAL PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  personnummer VARCHAR(13) NOT NULL UNIQUE
);