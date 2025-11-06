/connect ladok

CREATE TABLE IF NOT EXISTS public.results (
    id integer NOT NULL,
    personnummer character varying(13) NOT NULL,
    kurskod character varying(20) NOT NULL,
    modul character varying(10) NOT NULL,
    datum date NOT NULL,
    betyg character varying(5) NOT NULL,
    status character varying(30) NOT NULL,
    created_at timestamp without time zone DEFAULT now()
);

-- SEQUENCE: public.results_id_seq
CREATE SEQUENCE IF NOT EXISTS public.results_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.results_id_seq OWNED BY public.results.id;

ALTER TABLE ONLY public.results
    ALTER COLUMN id SET DEFAULT nextval('public.results_id_seq'::regclass);

-- PRIMARY KEY
ALTER TABLE ONLY public.results
    ADD CONSTRAINT results_pkey PRIMARY KEY (id);
