\connect ladok

CREATE TABLE IF NOT EXISTS results (
  id SERIAL PRIMARY KEY,
  personnummer VARCHAR(13) NOT NULL,
  kurskod VARCHAR(20) NOT NULL,
  modul VARCHAR(10) NOT NULL,
  datum DATE NOT NULL,
  betyg VARCHAR(5) NOT NULL,
  status VARCHAR(30) NOT NULL,
  created_at TIMESTAMP DEFAULT NOW()
);