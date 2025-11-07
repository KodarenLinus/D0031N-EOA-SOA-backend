\connect epok

DROP TABLE IF EXISTS epok_module CASCADE;
DROP TABLE IF EXISTS epok_course CASCADE;

CREATE TABLE epok_course (
  id      SERIAL PRIMARY KEY,
  kurskod VARCHAR(6) UNIQUE NOT NULL
);

CREATE TABLE epok_module (
  id         SERIAL PRIMARY KEY,
  kurs_id    INT NOT NULL REFERENCES epok_course(id) ON DELETE CASCADE,
  modulkod   VARCHAR(4) NOT NULL,
  modulnamn  VARCHAR(80) NOT NULL,
  aktiv      BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT ux_epok_module UNIQUE (kurs_id, modulkod)
);

INSERT INTO epok_course (kurskod) VALUES ('I0015N'), ('D0017D'), ('T0020N');

INSERT INTO epok_module (kurs_id, modulkod, modulnamn, aktiv) VALUES
  (1,'0005','Inlämningsuppgift 3',TRUE),
  (1,'0006','Seminarium 1',TRUE),
  (1,'0007','Projektarbete',TRUE),
  (2,'0008','SQL-laboration',TRUE),
  (2,'0009','ER-modellering',TRUE),
  (2,'0010','Index och prestanda',TRUE),
  (3,'0011','Kravanalys',TRUE),
  (3,'0012','Prototypdesign',TRUE),
  (3,'0013','Slutrapport',TRUE);
