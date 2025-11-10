\connect studentits

DROP TABLE IF EXISTS its_person CASCADE;

CREATE TABLE its_person (
  id             SERIAL PRIMARY KEY,
  anvandarnamn   VARCHAR(64) UNIQUE NOT NULL,
  personnummer   VARCHAR(13) UNIQUE NOT NULL,
  fornamn        VARCHAR(40) NOT NULL,
  efternamn      VARCHAR(40) NOT NULL,
  CONSTRAINT chk_its_person_pnr_format CHECK (personnummer ~ '^[0-9]{8}-[0-9]{4}$')
);

INSERT INTO its_person (anvandarnamn, personnummer, fornamn, efternamn) VALUES
  ('linsid-4','20000215-1111','Linnea','Sjöberg'),
  ('emlind-5','19991201-2222','Emil','Lindgren'),
  ('karhal-6','20010310-3333','Karin','Hallberg'),
  ('adakri-7','20000505-4444','Adam','Kristoffersson'),
  ('sofber-8','20010721-5555','Sofie','Berg'),
  ('jonlar-9','19991212-6666','Jonas','Larsson'),
  ('matnil-10','20001101-7777','Matilda','Nilsson'),
  ('oskan-11','19991010-8888','Oskar','Andersson'),
  ('ellfre-12','20010403-9999','Ellen','Fredriksson'),
  ('vicper-13','20000622-1010','Victor','Persson'),
  ('alvlun-14','19991114-2020','Alva','Lundgren'),
  ('felkar-15','20010217-3030','Felix','Karlsson');
