\connect epok

INSERT INTO course_modules (kurskod, modul_kod, modul_namn, hp, aktiv) VALUES
 ('D0031N','0005','Inlämningsuppgift',2.5,TRUE),
 ('D0031N','0007','LAB',1.0,TRUE)
ON CONFLICT DO NOTHING;