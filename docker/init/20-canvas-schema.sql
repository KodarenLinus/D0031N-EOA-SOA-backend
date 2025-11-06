/connect canvas

CREATE TABLE IF NOT EXISTS public.assignment (
    assignment_id integer NOT NULL,
    module_id integer,
    name character varying(255),
    scale_hint character varying(255),
    type character varying(50),
    submission_deadline timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

-- SEQUENCE: public.assignment_assignment_id_seq
CREATE SEQUENCE IF NOT EXISTS public.assignment_assignment_id_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

ALTER SEQUENCE public.assignment_assignment_id_seq OWNED BY public.assignment.assignment_id;

ALTER TABLE ONLY public.assignment
    ALTER COLUMN assignment_id SET DEFAULT nextval('public.assignment_assignment_id_seq'::regclass);

-- TABLE: public.course
CREATE TABLE IF NOT EXISTS public.course (
    course_id integer NOT NULL,
    course_code character varying(50),
    name character varying(255),
    term character varying(50),
    start_date date,
    end_date date,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

-- SEQUENCE: public.course_course_id_seq
CREATE SEQUENCE IF NOT EXISTS public.course_course_id_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

ALTER SEQUENCE public.course_course_id_seq OWNED BY public.course.course_id;

ALTER TABLE ONLY public.course
    ALTER COLUMN course_id SET DEFAULT nextval('public.course_course_id_seq'::regclass);

-- TABLE: public.course_registration
CREATE TABLE IF NOT EXISTS public.course_registration (
    registration_id integer NOT NULL,
    student_id character varying(50),
    course_id integer,
    registration_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

-- SEQUENCE: public.course_registration_registration_id_seq
CREATE SEQUENCE IF NOT EXISTS public.course_registration_registration_id_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

ALTER SEQUENCE public.course_registration_registration_id_seq OWNED BY public.course_registration.registration_id;

ALTER TABLE ONLY public.course_registration
    ALTER COLUMN registration_id SET DEFAULT nextval('public.course_registration_registration_id_seq'::regclass);

-- TABLE: public.grade
CREATE TABLE IF NOT EXISTS public.grade (
    grade_id integer NOT NULL,
    submission_id integer NOT NULL,
    grade character varying(10) NOT NULL,
    comment text,
    graded_at timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

-- SEQUENCE: public.grade_grade_id_seq
CREATE SEQUENCE IF NOT EXISTS public.grade_grade_id_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

ALTER SEQUENCE public.grade_grade_id_seq OWNED BY public.grade.grade_id;

ALTER TABLE ONLY public.grade
    ALTER COLUMN grade_id SET DEFAULT nextval('public.grade_grade_id_seq'::regclass);

-- TABLE: public.module
CREATE TABLE IF NOT EXISTS public.module (
    module_id integer NOT NULL,
    course_id integer,
    module_code character varying(50),
    name character varying(255),
    description text,
    credit_points numeric(3,1),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

-- SEQUENCE: public.module_module_id_seq
CREATE SEQUENCE IF NOT EXISTS public.module_module_id_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

ALTER SEQUENCE public.module_module_id_seq OWNED BY public.module.module_id;

ALTER TABLE ONLY public.module
    ALTER COLUMN module_id SET DEFAULT nextval('public.module_module_id_seq'::regclass);

-- TABLE: public.student
CREATE TABLE IF NOT EXISTS public.student (
    student_id character varying(50) NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    email character varying(255),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

-- TABLE: public.submission
CREATE TABLE IF NOT EXISTS public.submission (
    submission_id integer NOT NULL,
    assignment_id integer,
    student_id character varying(50),
    submission_date timestamp without time zone,
    document_url text,
    grade character varying(10),
    comment text,
    graded_at timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

-- SEQUENCE: public.submission_submission_id_seq
CREATE SEQUENCE IF NOT EXISTS public.submission_submission_id_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

ALTER SEQUENCE public.submission_submission_id_seq OWNED BY public.submission.submission_id;

ALTER TABLE ONLY public.submission
    ALTER COLUMN submission_id SET DEFAULT nextval('public.submission_submission_id_seq'::regclass);

-- PRIMARY KEYS & UNIQUE
ALTER TABLE ONLY public.assignment
    ADD CONSTRAINT assignment_pkey PRIMARY KEY (assignment_id);

ALTER TABLE ONLY public.course
    ADD CONSTRAINT course_pkey PRIMARY KEY (course_id);

ALTER TABLE ONLY public.course
    ADD CONSTRAINT course_course_code_key UNIQUE (course_code);

ALTER TABLE ONLY public.course_registration
    ADD CONSTRAINT course_registration_pkey PRIMARY KEY (registration_id);

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_pkey PRIMARY KEY (grade_id);

ALTER TABLE ONLY public.module
    ADD CONSTRAINT module_pkey PRIMARY KEY (module_id);

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (student_id);

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_email_key UNIQUE (email);

ALTER TABLE ONLY public.submission
    ADD CONSTRAINT submission_pkey PRIMARY KEY (submission_id);

ALTER TABLE ONLY public.submission
    ADD CONSTRAINT uq_submission_assignment_student UNIQUE (assignment_id, student_id);

-- INDEXES
CREATE INDEX IF NOT EXISTS ix_assignment_module ON public.assignment USING btree (module_id);
CREATE INDEX IF NOT EXISTS ix_grade_submission  ON public.grade      USING btree (submission_id);
CREATE INDEX IF NOT EXISTS ix_module_course     ON public.module     USING btree (course_id);
CREATE INDEX IF NOT EXISTS ix_reg_course        ON public.course_registration USING btree (course_id);
CREATE INDEX IF NOT EXISTS ix_reg_student       ON public.course_registration USING btree (student_id);
CREATE INDEX IF NOT EXISTS ix_sub_assignment    ON public.submission USING btree (assignment_id);
CREATE INDEX IF NOT EXISTS ix_sub_student       ON public.submission USING btree (student_id);

-- FOREIGN KEYS
ALTER TABLE ONLY public.assignment
    ADD CONSTRAINT assignment_module_id_fkey
    FOREIGN KEY (module_id) REFERENCES public.module(module_id);

ALTER TABLE ONLY public.course_registration
    ADD CONSTRAINT course_registration_course_id_fkey
    FOREIGN KEY (course_id) REFERENCES public.course(course_id);

ALTER TABLE ONLY public.course_registration
    ADD CONSTRAINT course_registration_student_id_fkey
    FOREIGN KEY (student_id) REFERENCES public.student(student_id);

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_submission_id_fkey
    FOREIGN KEY (submission_id) REFERENCES public.submission(submission_id) ON DELETE CASCADE;

ALTER TABLE ONLY public.module
    ADD CONSTRAINT module_course_id_fkey
    FOREIGN KEY (course_id) REFERENCES public.course(course_id);

ALTER TABLE ONLY public.submission
    ADD CONSTRAINT submission_assignment_id_fkey
    FOREIGN KEY (assignment_id) REFERENCES public.assignment(assignment_id);

ALTER TABLE ONLY public.submission
    ADD CONSTRAINT submission_student_id_fkey
    FOREIGN KEY (student_id) REFERENCES public.student(student_id);
