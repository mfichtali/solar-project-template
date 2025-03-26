--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 17.0

-- Started on 2025-01-21 13:48:06

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE "solar-auth-dev";
--
-- TOC entry 3421 (class 1262 OID 16384)
-- Name: solar-auth-dev; Type: DATABASE; Schema: -; Owner: d_auth_user
--

CREATE DATABASE "solar-auth-dev" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE "solar-auth-dev" OWNER TO d_auth_user;

\connect -reuse-previous=on "dbname='solar-auth-dev'"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: d_auth_user
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO d_auth_user;

--
-- TOC entry 3422 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: d_auth_user
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 16441)
-- Name: auth_privilege; Type: TABLE; Schema: public; Owner: d_auth_user
--

CREATE TABLE public.auth_privilege (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    creation_date timestamp without time zone,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.auth_privilege OWNER TO d_auth_user;

--
-- TOC entry 215 (class 1259 OID 16440)
-- Name: auth_privilege_id_seq; Type: SEQUENCE; Schema: public; Owner: d_auth_user
--

CREATE SEQUENCE public.auth_privilege_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auth_privilege_id_seq OWNER TO d_auth_user;

--
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 215
-- Name: auth_privilege_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: d_auth_user
--

ALTER SEQUENCE public.auth_privilege_id_seq OWNED BY public.auth_privilege.id;


--
-- TOC entry 223 (class 1259 OID 16484)
-- Name: auth_refresh_token; Type: TABLE; Schema: public; Owner: d_auth_user
--

CREATE TABLE public.auth_refresh_token (
    id integer NOT NULL,
    login character varying(50) NOT NULL,
    user_id integer NOT NULL,
    token character varying(100) NOT NULL,
    expired_date timestamp without time zone NOT NULL,
    creation_date timestamp without time zone,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.auth_refresh_token OWNER TO d_auth_user;

--
-- TOC entry 222 (class 1259 OID 16483)
-- Name: auth_refresh_token_id_seq; Type: SEQUENCE; Schema: public; Owner: d_auth_user
--

CREATE SEQUENCE public.auth_refresh_token_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auth_refresh_token_id_seq OWNER TO d_auth_user;

--
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 222
-- Name: auth_refresh_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: d_auth_user
--

ALTER SEQUENCE public.auth_refresh_token_id_seq OWNED BY public.auth_refresh_token.id;


--
-- TOC entry 211 (class 1259 OID 16414)
-- Name: auth_role; Type: TABLE; Schema: public; Owner: d_auth_user
--

CREATE TABLE public.auth_role (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    creation_date timestamp without time zone,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.auth_role OWNER TO d_auth_user;

--
-- TOC entry 210 (class 1259 OID 16413)
-- Name: auth_role_id_seq; Type: SEQUENCE; Schema: public; Owner: d_auth_user
--

CREATE SEQUENCE public.auth_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auth_role_id_seq OWNER TO d_auth_user;

--
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 210
-- Name: auth_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: d_auth_user
--

ALTER SEQUENCE public.auth_role_id_seq OWNED BY public.auth_role.id;


--
-- TOC entry 219 (class 1259 OID 16452)
-- Name: auth_role_privilege; Type: TABLE; Schema: public; Owner: d_auth_user
--

CREATE TABLE public.auth_role_privilege (
    role_id integer NOT NULL,
    privilege_id integer NOT NULL
);


ALTER TABLE public.auth_role_privilege OWNER TO d_auth_user;

--
-- TOC entry 218 (class 1259 OID 16451)
-- Name: auth_role_privilege_privilege_id_seq; Type: SEQUENCE; Schema: public; Owner: d_auth_user
--

CREATE SEQUENCE public.auth_role_privilege_privilege_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auth_role_privilege_privilege_id_seq OWNER TO d_auth_user;

--
-- TOC entry 3426 (class 0 OID 0)
-- Dependencies: 218
-- Name: auth_role_privilege_privilege_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: d_auth_user
--

ALTER SEQUENCE public.auth_role_privilege_privilege_id_seq OWNED BY public.auth_role_privilege.privilege_id;


--
-- TOC entry 217 (class 1259 OID 16450)
-- Name: auth_role_privilege_role_id_seq; Type: SEQUENCE; Schema: public; Owner: d_auth_user
--

CREATE SEQUENCE public.auth_role_privilege_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auth_role_privilege_role_id_seq OWNER TO d_auth_user;

--
-- TOC entry 3427 (class 0 OID 0)
-- Dependencies: 217
-- Name: auth_role_privilege_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: d_auth_user
--

ALTER SEQUENCE public.auth_role_privilege_role_id_seq OWNED BY public.auth_role_privilege.role_id;


--
-- TOC entry 225 (class 1259 OID 16532)
-- Name: auth_user; Type: TABLE; Schema: public; Owner: d_auth_user
--

CREATE TABLE public.auth_user (
    id integer NOT NULL,
    firstname character varying(50) DEFAULT NULL::character varying,
    lastname character varying(50) DEFAULT NULL::character varying,
    email character varying(100) NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    token_origin text,
    token_uuid uuid,
    is_blocked boolean DEFAULT false,
    date_blocking timestamp without time zone,
    date_unblocking timestamp without time zone,
    is_token_expired boolean DEFAULT false,
    is_account_active boolean DEFAULT false,
    activation_date timestamp without time zone,
    nbr_attempt_password integer DEFAULT 0,
    last_db_connect timestamp without time zone,
    uuid_user_hash character varying(50) DEFAULT NULL::character varying,
    creation_date timestamp without time zone,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.auth_user OWNER TO d_auth_user;

--
-- TOC entry 224 (class 1259 OID 16531)
-- Name: auth_user_id_seq1; Type: SEQUENCE; Schema: public; Owner: d_auth_user
--

CREATE SEQUENCE public.auth_user_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auth_user_id_seq1 OWNER TO d_auth_user;

--
-- TOC entry 3428 (class 0 OID 0)
-- Dependencies: 224
-- Name: auth_user_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: d_auth_user
--

ALTER SEQUENCE public.auth_user_id_seq1 OWNED BY public.auth_user.id;


--
-- TOC entry 221 (class 1259 OID 16468)
-- Name: auth_user_passwords; Type: TABLE; Schema: public; Owner: d_auth_user
--

CREATE TABLE public.auth_user_passwords (
    id integer NOT NULL,
    login character varying(50) DEFAULT NULL::character varying,
    password character varying(100) NOT NULL,
    date_change timestamp without time zone,
    user_id integer NOT NULL,
    creation_date timestamp without time zone,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.auth_user_passwords OWNER TO d_auth_user;

--
-- TOC entry 220 (class 1259 OID 16467)
-- Name: auth_user_passwords_id_seq; Type: SEQUENCE; Schema: public; Owner: d_auth_user
--

CREATE SEQUENCE public.auth_user_passwords_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auth_user_passwords_id_seq OWNER TO d_auth_user;

--
-- TOC entry 3429 (class 0 OID 0)
-- Dependencies: 220
-- Name: auth_user_passwords_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: d_auth_user
--

ALTER SEQUENCE public.auth_user_passwords_id_seq OWNED BY public.auth_user_passwords.id;


--
-- TOC entry 214 (class 1259 OID 16425)
-- Name: auth_user_role; Type: TABLE; Schema: public; Owner: d_auth_user
--

CREATE TABLE public.auth_user_role (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.auth_user_role OWNER TO d_auth_user;

--
-- TOC entry 213 (class 1259 OID 16424)
-- Name: auth_user_role_role_id_seq; Type: SEQUENCE; Schema: public; Owner: d_auth_user
--

CREATE SEQUENCE public.auth_user_role_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auth_user_role_role_id_seq OWNER TO d_auth_user;

--
-- TOC entry 3430 (class 0 OID 0)
-- Dependencies: 213
-- Name: auth_user_role_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: d_auth_user
--

ALTER SEQUENCE public.auth_user_role_role_id_seq OWNED BY public.auth_user_role.role_id;


--
-- TOC entry 212 (class 1259 OID 16423)
-- Name: auth_user_role_user_id_seq; Type: SEQUENCE; Schema: public; Owner: d_auth_user
--

CREATE SEQUENCE public.auth_user_role_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auth_user_role_user_id_seq OWNER TO d_auth_user;

--
-- TOC entry 3431 (class 0 OID 0)
-- Dependencies: 212
-- Name: auth_user_role_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: d_auth_user
--

ALTER SEQUENCE public.auth_user_role_user_id_seq OWNED BY public.auth_user_role.user_id;


--
-- TOC entry 209 (class 1259 OID 16385)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: d_auth_user
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO d_auth_user;

--
-- TOC entry 3209 (class 2604 OID 16444)
-- Name: auth_privilege id; Type: DEFAULT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_privilege ALTER COLUMN id SET DEFAULT nextval('public.auth_privilege_id_seq'::regclass);


--
-- TOC entry 3220 (class 2604 OID 16487)
-- Name: auth_refresh_token id; Type: DEFAULT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_refresh_token ALTER COLUMN id SET DEFAULT nextval('public.auth_refresh_token_id_seq'::regclass);


--
-- TOC entry 3203 (class 2604 OID 16417)
-- Name: auth_role id; Type: DEFAULT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_role ALTER COLUMN id SET DEFAULT nextval('public.auth_role_id_seq'::regclass);


--
-- TOC entry 3213 (class 2604 OID 16455)
-- Name: auth_role_privilege role_id; Type: DEFAULT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_role_privilege ALTER COLUMN role_id SET DEFAULT nextval('public.auth_role_privilege_role_id_seq'::regclass);


--
-- TOC entry 3214 (class 2604 OID 16456)
-- Name: auth_role_privilege privilege_id; Type: DEFAULT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_role_privilege ALTER COLUMN privilege_id SET DEFAULT nextval('public.auth_role_privilege_privilege_id_seq'::regclass);


--
-- TOC entry 3224 (class 2604 OID 16535)
-- Name: auth_user id; Type: DEFAULT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user ALTER COLUMN id SET DEFAULT nextval('public.auth_user_id_seq1'::regclass);


--
-- TOC entry 3215 (class 2604 OID 16471)
-- Name: auth_user_passwords id; Type: DEFAULT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user_passwords ALTER COLUMN id SET DEFAULT nextval('public.auth_user_passwords_id_seq'::regclass);


--
-- TOC entry 3207 (class 2604 OID 16428)
-- Name: auth_user_role user_id; Type: DEFAULT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user_role ALTER COLUMN user_id SET DEFAULT nextval('public.auth_user_role_user_id_seq'::regclass);


--
-- TOC entry 3208 (class 2604 OID 16429)
-- Name: auth_user_role role_id; Type: DEFAULT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user_role ALTER COLUMN role_id SET DEFAULT nextval('public.auth_user_role_role_id_seq'::regclass);


--
-- TOC entry 3406 (class 0 OID 16441)
-- Dependencies: 216
-- Data for Name: auth_privilege; Type: TABLE DATA; Schema: public; Owner: d_auth_user
--

COPY public.auth_privilege (id, name, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
1	WRITE_ADMIN	2025-01-10 20:26:11.592158	\N	system	\N	f
2	READ_ADMIN	2025-01-10 20:26:11.592158	\N	system	\N	f
3	EXECUTE_ADMIN	2025-01-10 20:26:11.592158	\N	system	\N	f
4	DELETE_ADMIN	2025-01-10 20:26:11.592158	\N	system	\N	f
\.


--
-- TOC entry 3413 (class 0 OID 16484)
-- Dependencies: 223
-- Data for Name: auth_refresh_token; Type: TABLE DATA; Schema: public; Owner: d_auth_user
--

COPY public.auth_refresh_token (id, login, user_id, token, expired_date, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
1	authdev	2	6681fe5d-9b22-40dd-8857-2765c55e30ad	2025-01-22 13:38:17.837241	2025-01-21 13:38:17.852	2025-01-21 13:38:17.852	system	system	f
2	authuser	1	922eadc6-4fd0-4e2e-8666-07c67060f5e9	2025-01-22 13:46:55.951282	2025-01-21 13:46:55.951	2025-01-21 13:46:55.951	system	system	f
\.


--
-- TOC entry 3401 (class 0 OID 16414)
-- Dependencies: 211
-- Data for Name: auth_role; Type: TABLE DATA; Schema: public; Owner: d_auth_user
--

COPY public.auth_role (id, name, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
1	ROLE_USER	2025-01-10 20:26:11.592158	\N	system	\N	f
2	ROLE_ADMIN	2025-01-10 20:26:11.592158	\N	system	\N	f
\.


--
-- TOC entry 3409 (class 0 OID 16452)
-- Dependencies: 219
-- Data for Name: auth_role_privilege; Type: TABLE DATA; Schema: public; Owner: d_auth_user
--

COPY public.auth_role_privilege (role_id, privilege_id) FROM stdin;
1	1
1	2
1	3
1	4
\.


--
-- TOC entry 3415 (class 0 OID 16532)
-- Dependencies: 225
-- Data for Name: auth_user; Type: TABLE DATA; Schema: public; Owner: d_auth_user
--

COPY public.auth_user (id, firstname, lastname, email, username, password, token_origin, token_uuid, is_blocked, date_blocking, date_unblocking, is_token_expired, is_account_active, activation_date, nbr_attempt_password, last_db_connect, uuid_user_hash, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
2	DEV_USER	DEV_USER	authdev@gmail.com	authdev	$2a$10$WQ7nUfm5ZJeqEvtn8mHra.B0oIVwmOwV5sDDRW43iYJoD8lyKH1R2	eyJhbGciOiJSUzUxMiJ9.eyJqdGkiOiJOakl6Tm1JMFpqY3RZakJrTnkwME1EY3hMV0k0WlRJdE5UbGpORGN3WXpJNU1UVXgiLCJzdWIiOiJhdXRoZGV2Iiwicm9sZXMiOlsiUk9MRV9VU0VSIiwiV1JJVEVfQURNSU4iXSwiaWF0IjoxNzM3NDYzMDk3LCJleHAiOjE3Mzc0OTE4OTd9.ARU1QAHAte7MmSi-R16U6m-AnmZJHHAn042o0sNzbBjZ-zqREmComvs1ZWanqalJSfe1SeZfHSvX9U4HQgieclMYYHVKhuGO2W3vXpmdJVbYbkg556eA9dpl5QRW_Riz3mjLFyHndNOmShYB32-1I74Fud5XB5Xq-3zzg3qtCowSexmimHiARS4BfHz5zyTZZUdvQvDCutf6PzN-C2cOv-Tnz6u65-qkdPQRK11tdZ7xcxNbTd_ELVXWaXjCBrbczngCUkrYRuARh6dOKTk1qRu8RkLL5mDjEdwP8QRM0Ga3GCzXTJ7rDM8iHjcISCTWXAFEeNWPpHhTJfOkjjG2wA	b55f6ecc-3722-3890-a612-797fd03484cf	f	\N	\N	f	t	2025-01-21 13:37:59.855582	0	2025-01-21 13:38:17.872638	f29eae0909cb3749a1863ebfabfb52a2	2025-01-21 13:32:46.164	2025-01-21 13:38:17.888	system	system	f
1	USER	USER	auth.user@yopmail.com	authuser	$2y$10$P64kPB8kuYYzFsfgj.A7IeVDSIBghdXuOxfknYndT9dXEq5lKIKzC	eyJhbGciOiJSUzUxMiJ9.eyJqdGkiOiJOVEE0TURJd1kyRXROREpoTnkwMFl6ZzRMV0ppWmpNdFlqVXpZMk5pTWpVM05XUmkiLCJzdWIiOiJhdXRodXNlciIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iLCJXUklURV9BRE1JTiJdLCJpYXQiOjE3Mzc0NjM2MTUsImV4cCI6MTczNzQ5MjQxNX0.aN8g8dzOzY7-g8KmtzpkH2v9vnh5N87vfbiSvH6N1smmzDefhBPYmjueA-4ywPYgaGTcwYNkDiLe8KjH-Ym-_BNc0sD-cc7YsmiJHdJcSeh9iizcM5ljKVanyR7EYj2lyeFDG0G_KsntJY22xrLnxFz1y8D-EhWbFIw_N9WrAF3SAUy3GMN6FH6Ga-rDrKfBl1XmTZLtquqnbBdtMMXQAyjzVnoYBTbczqwZaEdNSW8j6Y-mr_a73VOEuHI_1kDJSMA1AFs6tDbTFS4uSh8bw1clGfSZ1QEVuYqGkc6uI00pfZMcr9y-8sy366qR5Pvc7RJoClU-9H0QR2nynhwJyQ	92f4e878-6eee-3507-92bf-c5e1c45edcbd	f	\N	\N	f	t	2025-01-21 13:46:40.248493	0	2025-01-21 13:46:55.960526	f29eae0909cb3749a1863ebfabfb52a3	2025-01-10 20:26:11.592158	2025-01-21 13:46:55.975	system	system	f
\.


--
-- TOC entry 3411 (class 0 OID 16468)
-- Dependencies: 221
-- Data for Name: auth_user_passwords; Type: TABLE DATA; Schema: public; Owner: d_auth_user
--

COPY public.auth_user_passwords (id, login, password, date_change, user_id, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
1	authdev	$2a$10$WQ7nUfm5ZJeqEvtn8mHra.B0oIVwmOwV5sDDRW43iYJoD8lyKH1R2	2025-01-21 13:32:46.16309	2	2025-01-21 13:32:46.185	2025-01-21 13:32:46.185	system	system	f
\.


--
-- TOC entry 3404 (class 0 OID 16425)
-- Dependencies: 214
-- Data for Name: auth_user_role; Type: TABLE DATA; Schema: public; Owner: d_auth_user
--

COPY public.auth_user_role (user_id, role_id) FROM stdin;
1	1
1	2
2	1
\.


--
-- TOC entry 3399 (class 0 OID 16385)
-- Dependencies: 209
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: d_auth_user
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	init auth tables	SQL	V1__init_auth_tables.sql	713785338	d_auth_user	2025-01-10 20:26:10.893585	612	t
2	2	init data auth tables	SQL	V2__init_data_auth_tables.sql	-837198262	d_auth_user	2025-01-10 20:26:11.592158	84	t
3	3	add constraint uk auth user	SQL	V3__add_constraint_uk_auth_user.sql	-1263619055	d_auth_user	2025-01-10 20:26:11.712516	108	t
4	4	user table edit column	SQL	V4__user_table_edit_column.sql	1785280607	d_auth_user	2025-01-10 20:26:11.865471	248	t
\.


--
-- TOC entry 3432 (class 0 OID 0)
-- Dependencies: 215
-- Name: auth_privilege_id_seq; Type: SEQUENCE SET; Schema: public; Owner: d_auth_user
--

SELECT pg_catalog.setval('public.auth_privilege_id_seq', 1, false);


--
-- TOC entry 3433 (class 0 OID 0)
-- Dependencies: 222
-- Name: auth_refresh_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: d_auth_user
--

SELECT pg_catalog.setval('public.auth_refresh_token_id_seq', 2, true);


--
-- TOC entry 3434 (class 0 OID 0)
-- Dependencies: 210
-- Name: auth_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: d_auth_user
--

SELECT pg_catalog.setval('public.auth_role_id_seq', 1, false);


--
-- TOC entry 3435 (class 0 OID 0)
-- Dependencies: 218
-- Name: auth_role_privilege_privilege_id_seq; Type: SEQUENCE SET; Schema: public; Owner: d_auth_user
--

SELECT pg_catalog.setval('public.auth_role_privilege_privilege_id_seq', 1, false);


--
-- TOC entry 3436 (class 0 OID 0)
-- Dependencies: 217
-- Name: auth_role_privilege_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: d_auth_user
--

SELECT pg_catalog.setval('public.auth_role_privilege_role_id_seq', 1, false);


--
-- TOC entry 3437 (class 0 OID 0)
-- Dependencies: 224
-- Name: auth_user_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: d_auth_user
--

SELECT pg_catalog.setval('public.auth_user_id_seq1', 2, true);


--
-- TOC entry 3438 (class 0 OID 0)
-- Dependencies: 220
-- Name: auth_user_passwords_id_seq; Type: SEQUENCE SET; Schema: public; Owner: d_auth_user
--

SELECT pg_catalog.setval('public.auth_user_passwords_id_seq', 1, true);


--
-- TOC entry 3439 (class 0 OID 0)
-- Dependencies: 213
-- Name: auth_user_role_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: d_auth_user
--

SELECT pg_catalog.setval('public.auth_user_role_role_id_seq', 1, false);


--
-- TOC entry 3440 (class 0 OID 0)
-- Dependencies: 212
-- Name: auth_user_role_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: d_auth_user
--

SELECT pg_catalog.setval('public.auth_user_role_user_id_seq', 1, false);


--
-- TOC entry 3241 (class 2606 OID 16449)
-- Name: auth_privilege auth_privilege_pkey; Type: CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_privilege
    ADD CONSTRAINT auth_privilege_pkey PRIMARY KEY (id);


--
-- TOC entry 3245 (class 2606 OID 16492)
-- Name: auth_refresh_token auth_refresh_token_pkey; Type: CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_refresh_token
    ADD CONSTRAINT auth_refresh_token_pkey PRIMARY KEY (id);


--
-- TOC entry 3239 (class 2606 OID 16422)
-- Name: auth_role auth_role_pkey; Type: CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_role
    ADD CONSTRAINT auth_role_pkey PRIMARY KEY (id);


--
-- TOC entry 3243 (class 2606 OID 16477)
-- Name: auth_user_passwords auth_user_passwords_pkey; Type: CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user_passwords
    ADD CONSTRAINT auth_user_passwords_pkey PRIMARY KEY (id);


--
-- TOC entry 3249 (class 2606 OID 16549)
-- Name: auth_user auth_user_pkey1; Type: CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT auth_user_pkey1 PRIMARY KEY (id);


--
-- TOC entry 3236 (class 2606 OID 16392)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 3247 (class 2606 OID 16494)
-- Name: auth_refresh_token refresh_token_uk; Type: CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_refresh_token
    ADD CONSTRAINT refresh_token_uk UNIQUE (token);


--
-- TOC entry 3251 (class 2606 OID 16551)
-- Name: auth_user uk_email; Type: CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT uk_email UNIQUE (email);


--
-- TOC entry 3253 (class 2606 OID 16553)
-- Name: auth_user uk_username; Type: CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT uk_username UNIQUE (username);


--
-- TOC entry 3237 (class 1259 OID 16393)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: d_auth_user
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 3254 (class 2606 OID 16435)
-- Name: auth_user_role auth_role_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user_role
    ADD CONSTRAINT auth_role_fk FOREIGN KEY (role_id) REFERENCES public.auth_role(id);


--
-- TOC entry 3255 (class 2606 OID 16554)
-- Name: auth_user_role auth_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user_role
    ADD CONSTRAINT auth_user_fk FOREIGN KEY (user_id) REFERENCES public.auth_user(id);


--
-- TOC entry 3258 (class 2606 OID 16559)
-- Name: auth_user_passwords auth_user_password_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_user_passwords
    ADD CONSTRAINT auth_user_password_fk FOREIGN KEY (user_id) REFERENCES public.auth_user(id);


--
-- TOC entry 3256 (class 2606 OID 16462)
-- Name: auth_role_privilege rp_privilege_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_role_privilege
    ADD CONSTRAINT rp_privilege_fk FOREIGN KEY (privilege_id) REFERENCES public.auth_privilege(id);


--
-- TOC entry 3257 (class 2606 OID 16457)
-- Name: auth_role_privilege rp_role_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_role_privilege
    ADD CONSTRAINT rp_role_fk FOREIGN KEY (role_id) REFERENCES public.auth_role(id);


--
-- TOC entry 3259 (class 2606 OID 16564)
-- Name: auth_refresh_token user_refresh_token_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_auth_user
--

ALTER TABLE ONLY public.auth_refresh_token
    ADD CONSTRAINT user_refresh_token_fk FOREIGN KEY (user_id) REFERENCES public.auth_user(id);


-- Completed on 2025-01-21 13:48:07

--
-- PostgreSQL database dump complete
--

