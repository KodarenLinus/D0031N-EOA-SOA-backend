/connect epok

CREATE TABLE IF NOT EXISTS public.course_modules (
    id integer NOT NULL,
    kurskod character varying(20) NOT NULL,
    modul_kod character varying(10) NOT NULL,
    modul_namn character varying(200) NOT NULL,
    hp numeric(4,1),
    aktiv boolean DEFAULT true NOT NULL
);

-- SEQUENCE: public.course_modules_id_seq
CREATE SEQUENCE IF NOT EXISTS public.course_modules_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.course_modules_id_seq
    OWNED BY public.course_modules.id;

ALTER TABLE ONLY public.course_modules
    ALTER COLUMN id SET DEFAULT nextval('public.course_modules_id_seq'::regclass);

-- PRIMARY KEY
ALTER TABLE ONLY public.course_modules
    ADD CONSTRAINT course_modules_pkey PRIMARY KEY (id);
