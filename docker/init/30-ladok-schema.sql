\connect ladok

DROP VIEW  IF EXISTS v_ladok_roster;
DROP TABLE IF EXISTS ladok_resultat, ladok_registrering, ladok_kursmodul,
                    ladok_kurstillfalle, ladok_kurs, ladok_person CASCADE;

CREATE TABLE ladok_person (
  id            SERIAL PRIMARY KEY,
  personnummer  VARCHAR(13) UNIQUE NOT NULL,
  fornamn       VARCHAR(40) NOT NULL,
  efternamn     VARCHAR(40) NOT NULL,
  CONSTRAINT chk_ladok_person_pnr_format CHECK (personnummer ~ '^[0-9]{8}-[0-9]{4}$')
);

CREATE TABLE ladok_kurs (
  id       SERIAL PRIMARY KEY,
  kurskod  VARCHAR(6) UNIQUE NOT NULL
);

CREATE TABLE ladok_kurstillfalle (
  id         SERIAL PRIMARY KEY,
  kurs_id    INT NOT NULL REFERENCES ladok_kurs(id) ON DELETE CASCADE,
  instanskod VARCHAR(16) NOT NULL,
  CONSTRAINT ux_ladok_kurstillfalle UNIQUE (kurs_id, instanskod)
);

CREATE TABLE ladok_kursmodul (
  id         SERIAL PRIMARY KEY,
  kurs_id    INT NOT NULL REFERENCES ladok_kurs(id) ON DELETE CASCADE,
  modulkod   VARCHAR(4) NOT NULL,
  modulnamn  VARCHAR(80),
  CONSTRAINT ux_ladok_kursmodul UNIQUE (kurs_id, modulkod)
);

CREATE TABLE ladok_registrering (
  id                SERIAL PRIMARY KEY,
  person_id         INT NOT NULL REFERENCES ladok_person(id) ON DELETE CASCADE,
  kurstillfalle_id  INT NOT NULL REFERENCES ladok_kurstillfalle(id) ON DELETE CASCADE,
  status            VARCHAR(20) NOT NULL DEFAULT 'REGISTRERAD',
  CONSTRAINT ux_ladok_registrering UNIQUE (person_id, kurstillfalle_id)
);

CREATE TABLE ladok_resultat (
  id            SERIAL PRIMARY KEY,
  personnummer  VARCHAR(13) NOT NULL,
  kurskod       VARCHAR(6)  NOT NULL,
  modulkod      VARCHAR(4)  NOT NULL,
  datum         DATE        NOT NULL,
  betyg         VARCHAR(2)  NOT NULL,
  status        VARCHAR(30) NOT NULL DEFAULT 'registrerad',
  created_at    TIMESTAMP   NOT NULL DEFAULT now(),
  CONSTRAINT ux_ladok_resultat UNIQUE (personnummer, kurskod, modulkod),
  CONSTRAINT chk_ladok_resultat_pnr_format CHECK (personnummer ~ '^[0-9]{8}-[0-9]{4}$')
);

CREATE INDEX ix_ladok_reg_person        ON ladok_registrering(person_id);
CREATE INDEX ix_ladok_reg_kurstillfalle ON ladok_registrering(kurstillfalle_id);

CREATE OR REPLACE VIEW v_ladok_roster AS
SELECT
  kt.id            AS kurstillfalle_id,
  k.kurskod,
  kt.instanskod,
  p.personnummer,
  p.fornamn,
  p.efternamn,
  r.status
FROM ladok_registrering r
JOIN ladok_person p        ON p.id  = r.person_id
JOIN ladok_kurstillfalle kt ON kt.id = r.kurstillfalle_id
JOIN ladok_kurs k          ON k.id  = kt.kurs_id;

-- Seed: (för enkelhet, lägg in samma personer manuellt här
--  eller via dblink/FDW om du vill hämta från studentits)
INSERT INTO ladok_person (personnummer, fornamn, efternamn) VALUES
  ('20000215-1111','Linnea','Sjöberg'),
  ('19991201-2222','Emil','Lindgren'),
  ('20010310-3333','Karin','Hallberg'),
  ('20000505-4444','Adam','Kristoffersson'),
  ('20010721-5555','Sofie','Berg'),
  ('19991212-6666','Jonas','Larsson'),
  ('20001101-7777','Matilda','Nilsson'),
  ('19991010-8888','Oskar','Andersson'),
  ('20010403-9999','Ellen','Fredriksson'),
  ('20000622-1010','Victor','Persson'),
  ('19991114-2020','Alva','Lundgren'),
  ('20010217-3030','Felix','Karlsson');

INSERT INTO ladok_kurs (kurskod) VALUES ('I0015N'), ('D0017D'), ('T0020N');
INSERT INTO ladok_kurstillfalle (kurs_id, instanskod) VALUES (1,'KT1'),(2,'KT1'),(3,'KT1');

INSERT INTO ladok_kursmodul (kurs_id, modulkod, modulnamn) VALUES
  (1,'0005','Inlämningsuppgift 3'),
  (1,'0006','Seminarium 1'),
  (1,'0007','Projektarbete'),
  (2,'0008','SQL-laboration'),
  (2,'0009','ER-modellering'),
  (2,'0010','Index och prestanda'),
  (3,'0011','Kravanalys'),
  (3,'0012','Prototypdesign'),
  (3,'0013','Slutrapport');

INSERT INTO ladok_registrering (person_id, kurstillfalle_id) VALUES
  (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),
  (1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),
  (7,3),(8,3),(9,3),(10,3),(11,3),(12,3);
