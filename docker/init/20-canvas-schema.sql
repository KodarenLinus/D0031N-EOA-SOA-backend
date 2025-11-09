\connect canvas

DROP VIEW IF EXISTS v_canvas_roster;
DROP TABLE IF EXISTS canvas_grade, canvas_submission, canvas_assignment,
                    canvas_module, canvas_room_enrollment, canvas_room,
                    canvas_course, canvas_student CASCADE;

CREATE TABLE canvas_student (
  id            SERIAL PRIMARY KEY,
  anvandarnamn  VARCHAR(64) UNIQUE NOT NULL,
  fornamn       VARCHAR(40) NOT NULL,
  efternamn     VARCHAR(40) NOT NULL
);

CREATE TABLE canvas_course (
  id       SERIAL PRIMARY KEY,
  kurskod  VARCHAR(6) UNIQUE NOT NULL,
  namn     VARCHAR(100) NOT NULL
);

CREATE TABLE canvas_room (
  id         SERIAL PRIMARY KEY,
  course_id  INT NOT NULL REFERENCES canvas_course(id) ON DELETE CASCADE,
  instanskod VARCHAR(16) NOT NULL,
  CONSTRAINT ux_canvas_room UNIQUE (course_id, instanskod)
);

CREATE TABLE canvas_room_enrollment (
  id          SERIAL PRIMARY KEY,
  student_id  INT NOT NULL REFERENCES canvas_student(id) ON DELETE CASCADE,
  room_id     INT NOT NULL REFERENCES canvas_room(id) ON DELETE CASCADE,
  status      VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
  CONSTRAINT ux_canvas_room_enrollment UNIQUE (student_id, room_id)
);

CREATE TABLE canvas_module (
  id         SERIAL PRIMARY KEY,
  course_id  INT NOT NULL REFERENCES canvas_course(id) ON DELETE CASCADE,
  modulkod   VARCHAR(4) NOT NULL,
  modulnamn  VARCHAR(80),
  CONSTRAINT ux_canvas_module UNIQUE (course_id, modulkod)
);

CREATE TABLE canvas_assignment (
  id            SERIAL PRIMARY KEY,
  module_id     INT NOT NULL REFERENCES canvas_module(id) ON DELETE CASCADE,
  assignment_nr INT NOT NULL,
  name          VARCHAR(100) NOT NULL,
  type          VARCHAR(50),
  CONSTRAINT ux_canvas_assignment UNIQUE (module_id, assignment_nr)
);

CREATE TABLE canvas_submission (
  id            SERIAL PRIMARY KEY,
  assignment_id INT NOT NULL REFERENCES canvas_assignment(id) ON DELETE CASCADE,
  student_id    INT NOT NULL REFERENCES canvas_student(id) ON DELETE CASCADE,
  submitted_at  TIMESTAMP,
  omdome        VARCHAR(20),
  status        VARCHAR(20) NOT NULL DEFAULT 'SUBMITTED',
  CONSTRAINT ux_canvas_submission UNIQUE (assignment_id, student_id)
);

CREATE TABLE canvas_grade (
  id                   SERIAL PRIMARY KEY,
  submission_id        INT NOT NULL UNIQUE REFERENCES canvas_submission(id) ON DELETE CASCADE,
  grade                VARCHAR(2) NOT NULL,
  graded_at            TIMESTAMP NOT NULL DEFAULT now(),
  reported_to_ladok_at TIMESTAMP,
  ladok_status         VARCHAR(30),
  ladok_ref_id         INT
);

CREATE OR REPLACE VIEW v_canvas_roster AS
SELECT
  r.id          AS room_id,
  c.kurskod,
  r.instanskod,
  s.anvandarnamn,
  s.fornamn,
  s.efternamn,
  e.status
FROM canvas_room_enrollment e
JOIN canvas_student s ON s.id = e.student_id
JOIN canvas_room    r ON r.id = e.room_id
JOIN canvas_course  c ON c.id = r.course_id;

-- Seed data
INSERT INTO canvas_student (anvandarnamn, fornamn, efternamn) VALUES
  ('linsid-4','Linnea','Sjöberg'),
  ('emlind-5','Emil','Lindgren'),
  ('karhal-6','Karin','Hallberg'),
  ('adakri-7','Adam','Kristoffersson'),
  ('sofber-8','Sofie','Berg'),
  ('jonlar-9','Jonas','Larsson'),
  ('matnil-10','Matilda','Nilsson'),
  ('oskan-11','Oskar','Andersson'),
  ('ellfre-12','Ellen','Fredriksson'),
  ('vicper-13','Victor','Persson'),
  ('alvlun-14','Alva','Lundgren'),
  ('felkar-15','Felix','Karlsson');

INSERT INTO canvas_course (kurskod, namn) VALUES
  ('I0015N','Automatiserade UI-tester'),
  ('D0017D','Databasteknik'),
  ('T0020N','Systemanalys och design');

INSERT INTO canvas_room (course_id, instanskod) VALUES
  (1,'KT1'), (2,'KT1'), (3,'KT1');

INSERT INTO canvas_room_enrollment (student_id, room_id) VALUES
  (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),
  (1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),
  (7,3),(8,3),(9,3),(10,3),(11,3),(12,3);

INSERT INTO canvas_module (course_id, modulkod, modulnamn) VALUES
  (1,'0005','Inlämningsuppgift 3'),
  (1,'0006','Seminarium 1'),
  (1,'0007','Projektarbete'),
  (2,'0008','SQL-laboration'),
  (2,'0009','ER-modellering'),
  (2,'0010','Index och prestanda'),
  (3,'0011','Kravanalys'),
  (3,'0012','Prototypdesign'),
  (3,'0013','Slutrapport');

INSERT INTO canvas_assignment (module_id, assignment_nr, name, type) VALUES
  (1,1,'Playwright UI-test','Inlämning'),
  (2,1,'Reflektionsseminarium','Seminarium'),
  (3,1,'Projektinlämning','Projekt'),
  (4,1,'SQL-frågor','Laboration'),
  (5,1,'ER-modellering i Lucidchart','Inlämning'),
  (6,1,'Prestandajämförelse','Laboration'),
  (7,1,'Kravlista','Inlämning'),
  (8,1,'Wireframe-prototyp','Prototyp'),
  (9,1,'Slutrapport i PDF','Projekt');

INSERT INTO canvas_submission (assignment_id, student_id, submitted_at, omdome, status) VALUES
  (1,1,'2025-11-01 10:00',NULL,'SUBMITTED'),
  (1,2,'2025-11-01 11:00',NULL,'SUBMITTED'),
  (1,3,'2025-11-02 09:00',NULL,'SUBMITTED'),
  (1,4,'2025-11-02 10:30',NULL,'SUBMITTED'),
  (4,5,'2025-11-03 08:45',NULL,'SUBMITTED'),
  (4,6,'2025-11-03 09:00',NULL,'SUBMITTED'),
  (4,7,'2025-11-03 09:30',NULL,'SUBMITTED'),
  (4,8,'2025-11-03 10:15',NULL,'SUBMITTED'),
  (7,9,'2025-11-04 08:00',NULL,'SUBMITTED'),
  (7,10,'2025-11-04 09:15',NULL,'SUBMITTED'),
  (7,11,'2025-11-04 09:30',NULL,'SUBMITTED'),
  (7,12,'2025-11-04 10:00',NULL,'SUBMITTED');
