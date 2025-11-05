/connect studentits

CREATE TABLE IF NOT EXISTS public.student (
    student_id character varying(50) NOT NULL,
    personnummer character varying(12),
    first_name character varying(255),
    last_name character varying(255),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

-- PRIMARY KEY
ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (student_id);

-- UNIQUE INDEX ON personnummer (if not null)
CREATE UNIQUE INDEX IF NOT EXISTS uq_studentits_student_pnr
    ON public.student USING btree (personnummer)
    WHERE (personnummer IS NOT NULL);
