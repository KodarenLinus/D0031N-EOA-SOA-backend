insert into canvas.assignment(id, course_code, name, scale_hint, type)
values (1, 'D0031N', 'Uppgift 1', 'A-F', 'assignment')
on conflict (id) do nothing;

insert into canvas.grade(assignment_id, username, label, points, graded_at) values
(1, 'sveedz-4', 'A', 28, now() - interval '1 day'),
(1, 'anna1',   'C', 22, now() - interval '1 day'),
(1, 'kalle2',  'U',  5, now() - interval '1 day');