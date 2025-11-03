\connect epok

CREATE TABLE IF NOT EXISTS course_modules (
  id SERIAL PRIMARY KEY,
  kurskod VARCHAR(20) NOT NULL,
  modul_kod VARCHAR(10) NOT NULL,
  modul_namn VARCHAR(200) NOT NULL,
  hp NUMERIC(4,1),
  aktiv BOOLEAN NOT NULL DEFAULT TRUE
);