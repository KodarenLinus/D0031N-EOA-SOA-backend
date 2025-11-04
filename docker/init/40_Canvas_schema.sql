\connect canvas

create table if not exists canvas.assignment (
  id serial primary key,
  course_code text not null,
  name text not null,
  scale_hint text,
  type text
);

create table if not exists canvas.grade (
  id serial primary key,
  assignment_id int not null references canvas.assignment(id),
  username text not null,
  label text,
  points numeric,
  graded_at timestamp without time zone
);