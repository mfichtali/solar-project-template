--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 17.0

-- Started on 2025-01-21 13:47:23

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

DROP DATABASE "solar-mdm-dev";
--
-- TOC entry 3442 (class 1262 OID 16384)
-- Name: solar-mdm-dev; Type: DATABASE; Schema: -; Owner: d_mdm_user
--

CREATE DATABASE "solar-mdm-dev" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE "solar-mdm-dev" OWNER TO d_mdm_user;

\connect -reuse-previous=on "dbname='solar-mdm-dev'"

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
-- TOC entry 5 (class 2615 OID 24765)
-- Name: public; Type: SCHEMA; Schema: -; Owner: d_mdm_user
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO d_mdm_user;

--
-- TOC entry 3443 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: d_mdm_user
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 24920)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: d_mdm_user
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


ALTER TABLE public.flyway_schema_history OWNER TO d_mdm_user;

--
-- TOC entry 210 (class 1259 OID 24926)
-- Name: ref_calendar_billing; Type: TABLE; Schema: public; Owner: d_mdm_user
--

CREATE TABLE public.ref_calendar_billing (
    id uuid NOT NULL,
    version integer NOT NULL,
    vehicle_id uuid NOT NULL,
    ref_month character varying(2) NOT NULL,
    ref_year character varying(4) NOT NULL,
    mt_rental numeric(10,2) NOT NULL,
    mt_rental_with_tax numeric(10,2) NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.ref_calendar_billing OWNER TO d_mdm_user;

--
-- TOC entry 211 (class 1259 OID 24932)
-- Name: ref_caution_billing; Type: TABLE; Schema: public; Owner: d_mdm_user
--

CREATE TABLE public.ref_caution_billing (
    id uuid NOT NULL,
    version integer NOT NULL,
    vehicle_id uuid NOT NULL,
    ld_start_caution_h timestamp without time zone NOT NULL,
    ld_end_caution_h timestamp without time zone,
    mt_caution numeric(10,2) NOT NULL,
    mt_caution_with_tax numeric(10,2) NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.ref_caution_billing OWNER TO d_mdm_user;

--
-- TOC entry 212 (class 1259 OID 24938)
-- Name: ref_endpoint; Type: TABLE; Schema: public; Owner: d_mdm_user
--

CREATE TABLE public.ref_endpoint (
    id uuid NOT NULL,
    version integer NOT NULL,
    co_api character varying(30) NOT NULL,
    path_endpoint character varying(200) NOT NULL,
    module_id uuid NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    date_activation timestamp without time zone,
    creation_date timestamp without time zone,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false NOT NULL
);


ALTER TABLE public.ref_endpoint OWNER TO d_mdm_user;

--
-- TOC entry 213 (class 1259 OID 24945)
-- Name: ref_gray_card; Type: TABLE; Schema: public; Owner: d_mdm_user
--

CREATE TABLE public.ref_gray_card (
    id uuid NOT NULL,
    version integer NOT NULL,
    vehicle_id uuid NOT NULL,
    owner character varying(10) NOT NULL,
    address character varying(100) NOT NULL,
    ref_usage character varying(10) NOT NULL,
    ld_start_circulation_maroc timestamp without time zone NOT NULL,
    ld_date_end_card_validity timestamp without time zone NOT NULL,
    ld_date_operation timestamp without time zone NOT NULL,
    serial_operation_number character varying(20) NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.ref_gray_card OWNER TO d_mdm_user;

--
-- TOC entry 214 (class 1259 OID 24951)
-- Name: ref_module; Type: TABLE; Schema: public; Owner: d_mdm_user
--

CREATE TABLE public.ref_module (
    id uuid NOT NULL,
    version integer NOT NULL,
    code character varying(20) NOT NULL,
    api_code character varying(20) NOT NULL,
    label character varying(50) NOT NULL,
    creation_date timestamp without time zone,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.ref_module OWNER TO d_mdm_user;

--
-- TOC entry 215 (class 1259 OID 24957)
-- Name: ref_vehicle; Type: TABLE; Schema: public; Owner: d_mdm_user
--

CREATE TABLE public.ref_vehicle (
    id uuid NOT NULL,
    version integer NOT NULL,
    marque character varying(50) NOT NULL,
    model character varying(50) NOT NULL,
    finishing character varying(100) NOT NULL,
    registration_number character varying(50) DEFAULT NULL::character varying,
    ld_change_registration_number timestamp without time zone,
    ww_registration_number character varying(50) NOT NULL,
    co_energy_type character varying(10) NOT NULL,
    co_gearbox_type character varying(10) NOT NULL,
    co_vehicle_type character varying(10) NOT NULL,
    ld_start_circulation timestamp without time zone NOT NULL,
    mileage integer NOT NULL,
    number_cylinders integer NOT NULL,
    number_places integer NOT NULL,
    serial_chassis_number character varying(50) NOT NULL,
    color character varying(10) NOT NULL,
    fiscal_power character varying(10) NOT NULL,
    din_power character varying(10) NOT NULL,
    mt_purchase numeric(10,2) NOT NULL,
    mt_purchase_with_tax numeric(10,2) NOT NULL,
    contact_purchase_reference character varying(50) NOT NULL,
    commentary_content text NOT NULL,
    in_active_operational boolean DEFAULT true,
    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.ref_vehicle OWNER TO d_mdm_user;

--
-- TOC entry 216 (class 1259 OID 24967)
-- Name: ref_version; Type: TABLE; Schema: public; Owner: d_mdm_user
--

CREATE TABLE public.ref_version (
    id uuid NOT NULL,
    version integer NOT NULL,
    version_number character varying(20) NOT NULL,
    module_id uuid NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    date_activation timestamp without time zone,
    creation_date timestamp without time zone,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false NOT NULL
);


ALTER TABLE public.ref_version OWNER TO d_mdm_user;

--
-- TOC entry 217 (class 1259 OID 24974)
-- Name: t_blob_storage; Type: TABLE; Schema: public; Owner: d_mdm_user
--

CREATE TABLE public.t_blob_storage (
    id uuid NOT NULL,
    version integer NOT NULL,
    co_document_type character varying(20) NOT NULL,
    document_name character varying(50) NOT NULL,
    document_extension character varying(10) NOT NULL,
    id_object uuid NOT NULL,
    ref_object character varying(50) DEFAULT NULL::character varying,
    blob_content bytea NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.t_blob_storage OWNER TO d_mdm_user;

--
-- TOC entry 218 (class 1259 OID 24983)
-- Name: t_monitoring_param_version; Type: TABLE; Schema: public; Owner: d_mdm_user
--

CREATE TABLE public.t_monitoring_param_version (
    id uuid NOT NULL,
    version integer NOT NULL,
    ref_version_id uuid NOT NULL,
    ref_endpoint_id uuid NOT NULL,
    is_active boolean DEFAULT true,
    is_lock boolean DEFAULT false,
    date_lock timestamp without time zone,
    creation_date timestamp without time zone,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.t_monitoring_param_version OWNER TO d_mdm_user;

--
-- TOC entry 219 (class 1259 OID 24991)
-- Name: t_vehicle_event_hist; Type: TABLE; Schema: public; Owner: d_mdm_user
--

CREATE TABLE public.t_vehicle_event_hist (
    id uuid NOT NULL,
    version integer NOT NULL,
    vehicle_id uuid NOT NULL,
    co_event_type character varying(10) NOT NULL,
    ts_start_event_h timestamp without time zone NOT NULL,
    ts_end_event_h timestamp without time zone,
    lb_event character varying(100) DEFAULT NULL::character varying,
    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);


ALTER TABLE public.t_vehicle_event_hist OWNER TO d_mdm_user;

--
-- TOC entry 3426 (class 0 OID 24920)
-- Dependencies: 209
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	init mdm schema	SQL	V1__init_mdm_schema.sql	2010401722	d_mdm_user	2025-01-10 20:18:46.170197	120	t
2	2	Endpoint table update v2	SQL	V2__Endpoint_table_update_v2.sql	-214778789	d_mdm_user	2025-01-10 20:18:46.31798	111	t
3	3	Version table update v2	SQL	V3__Version_table_update_v2.sql	-1921914081	d_mdm_user	2025-01-10 20:18:46.464948	118	t
4	4	Init param api version table	SQL	V4__Init_param_api_version_table.sql	-311764217	d_mdm_user	2025-01-10 20:18:46.612468	71	t
5	5	Endpoint table add column v3	SQL	V5__Endpoint_table_add_column_v3.sql	992875417	d_mdm_user	2025-01-10 20:18:46.700289	77	t
6	6	Version table add column v3	SQL	V6__Version_table_add_column_v3.sql	-677895786	d_mdm_user	2025-01-10 20:18:46.807989	67	t
7	7	Param-api-verion table add column v2	SQL	V7__Param-api-verion_table_add_column_v2.sql	1844978251	d_mdm_user	2025-01-10 20:18:46.898366	53	t
8	8	Audit table	SQL	V8__Audit_table.sql	-873647587	d_mdm_user	2025-01-10 20:18:46.97202	41	t
9	9	Endpoint add new column v4	SQL	V9__Endpoint_add_new_column_v4.sql	1074300317	d_mdm_user	2025-01-10 20:18:47.022806	134	t
10	10	init vehicle cation calendar tables	SQL	V10__init_vehicle_cation_calendar_tables.sql	17805080	d_mdm_user	2025-01-10 20:18:47.200114	235	t
11	11	delete audit tracer	SQL	V11__delete_audit_tracer.sql	300565069	d_mdm_user	2025-01-10 20:18:47.443073	8	t
12	12	table blob storage	SQL	V12__table_blob_storage.sql	-1033418058	d_mdm_user	2025-01-10 20:18:47.478703	47	t
13	13	Endpoint update column	SQL	V13__Endpoint_update_column.sql	73443273	d_mdm_user	2025-01-16 13:00:29.919519	203	t
\.


--
-- TOC entry 3427 (class 0 OID 24926)
-- Dependencies: 210
-- Data for Name: ref_calendar_billing; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.ref_calendar_billing (id, version, vehicle_id, ref_month, ref_year, mt_rental, mt_rental_with_tax, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
\.


--
-- TOC entry 3428 (class 0 OID 24932)
-- Dependencies: 211
-- Data for Name: ref_caution_billing; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.ref_caution_billing (id, version, vehicle_id, ld_start_caution_h, ld_end_caution_h, mt_caution, mt_caution_with_tax, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
\.


--
-- TOC entry 3429 (class 0 OID 24938)
-- Dependencies: 212
-- Data for Name: ref_endpoint; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.ref_endpoint (id, version, co_api, path_endpoint, module_id, is_active, date_activation, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
8723e086-3711-4a26-8f91-a717048421dd	0	auth	/api/login	a68d953b-5f40-4dba-a8f6-6545740bef68	t	2025-01-16 14:24:50.776365	2025-01-16 14:24:50.821	2025-01-16 14:24:50.821	SYSTEM	SYSTEM	f
7c1ab799-15d9-463b-99b5-cf18e609aac3	0	auth	/api/token/info/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}	a68d953b-5f40-4dba-a8f6-6545740bef68	t	2025-01-16 14:24:50.776365	2025-01-16 14:24:50.830259	2025-01-16 14:24:50.829259	SYSTEM	SYSTEM	f
a9425413-a393-4b0b-ac40-0adf76e9ad6a	0	auth	/api/user/register	a68d953b-5f40-4dba-a8f6-6545740bef68	t	2025-01-16 14:24:50.776365	2025-01-16 14:24:50.830259	2025-01-16 14:24:50.830259	SYSTEM	SYSTEM	f
a84e874c-72c0-42b0-8611-315827ec6f20	0	auth	/api/token/refresh	a68d953b-5f40-4dba-a8f6-6545740bef68	t	2025-01-16 14:24:50.776365	2025-01-16 14:24:50.830259	2025-01-16 14:24:50.830259	SYSTEM	SYSTEM	f
b079fdb2-03d4-45d2-8271-7a5e96c5a4f3	0	auth	/api/user/[a-z0-9]+/activate/[a-f0-9]{32}	a68d953b-5f40-4dba-a8f6-6545740bef68	t	2025-01-16 14:24:50.776365	2025-01-16 14:24:50.830259	2025-01-16 14:24:50.830259	SYSTEM	SYSTEM	f
1176a98a-4a87-4179-a04c-ea9dcce85729	0	auth	/api/fetch/public-key	a68d953b-5f40-4dba-a8f6-6545740bef68	t	2025-01-16 14:24:50.776365	2025-01-16 14:24:50.831258	2025-01-16 14:24:50.831258	SYSTEM	SYSTEM	f
\.


--
-- TOC entry 3430 (class 0 OID 24945)
-- Dependencies: 213
-- Data for Name: ref_gray_card; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.ref_gray_card (id, version, vehicle_id, owner, address, ref_usage, ld_start_circulation_maroc, ld_date_end_card_validity, ld_date_operation, serial_operation_number, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
\.


--
-- TOC entry 3431 (class 0 OID 24951)
-- Dependencies: 214
-- Data for Name: ref_module; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.ref_module (id, version, code, api_code, label, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
a68d953b-5f40-4dba-a8f6-6545740bef68	0	AUTH	auth	Authentication Service Provider	2025-01-16 08:26:27.487596	2025-01-16 08:26:27.48756	SYSTEM	SYSTEM	f
\.


--
-- TOC entry 3432 (class 0 OID 24957)
-- Dependencies: 215
-- Data for Name: ref_vehicle; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.ref_vehicle (id, version, marque, model, finishing, registration_number, ld_change_registration_number, ww_registration_number, co_energy_type, co_gearbox_type, co_vehicle_type, ld_start_circulation, mileage, number_cylinders, number_places, serial_chassis_number, color, fiscal_power, din_power, mt_purchase, mt_purchase_with_tax, contact_purchase_reference, commentary_content, in_active_operational, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
\.


--
-- TOC entry 3433 (class 0 OID 24967)
-- Dependencies: 216
-- Data for Name: ref_version; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.ref_version (id, version, version_number, module_id, is_active, date_activation, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
34b849ee-71b9-479c-9252-04b6ac0fda4f	0	1.0	a68d953b-5f40-4dba-a8f6-6545740bef68	t	2025-01-16 14:24:50.779405	2025-01-16 14:24:50.835788	2025-01-16 14:24:50.835788	SYSTEM	SYSTEM	f
\.


--
-- TOC entry 3434 (class 0 OID 24974)
-- Dependencies: 217
-- Data for Name: t_blob_storage; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.t_blob_storage (id, version, co_document_type, document_name, document_extension, id_object, ref_object, blob_content, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
\.


--
-- TOC entry 3435 (class 0 OID 24983)
-- Dependencies: 218
-- Data for Name: t_monitoring_param_version; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.t_monitoring_param_version (id, version, ref_version_id, ref_endpoint_id, is_active, is_lock, date_lock, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
a8f5dd89-de9c-4217-ada4-5f565d9616ff	0	34b849ee-71b9-479c-9252-04b6ac0fda4f	8723e086-3711-4a26-8f91-a717048421dd	t	f	\N	2025-01-16 14:24:51.114821	2025-01-16 14:24:51.114821	SYSTEM	SYSTEM	f
f2ce579a-1e23-4d23-bd7d-71161d763870	0	34b849ee-71b9-479c-9252-04b6ac0fda4f	7c1ab799-15d9-463b-99b5-cf18e609aac3	t	f	\N	2025-01-16 14:24:51.116331	2025-01-16 14:24:51.115823	SYSTEM	SYSTEM	f
52b50794-fbf5-48b9-be69-109c4154ffe3	0	34b849ee-71b9-479c-9252-04b6ac0fda4f	a9425413-a393-4b0b-ac40-0adf76e9ad6a	t	f	\N	2025-01-16 14:24:51.11734	2025-01-16 14:24:51.11734	SYSTEM	SYSTEM	f
131f4c2f-b1f2-408d-81c3-ac1356d2231b	0	34b849ee-71b9-479c-9252-04b6ac0fda4f	a84e874c-72c0-42b0-8611-315827ec6f20	t	f	\N	2025-01-16 14:24:51.11734	2025-01-16 14:24:51.11734	SYSTEM	SYSTEM	f
efc3b97d-16b3-45bb-ba7e-23f1bd354000	0	34b849ee-71b9-479c-9252-04b6ac0fda4f	b079fdb2-03d4-45d2-8271-7a5e96c5a4f3	t	f	\N	2025-01-16 14:24:51.118345	2025-01-16 14:24:51.118345	SYSTEM	SYSTEM	f
77feb35d-25ad-481d-a5cb-899d745824e0	0	34b849ee-71b9-479c-9252-04b6ac0fda4f	1176a98a-4a87-4179-a04c-ea9dcce85729	t	f	\N	2025-01-16 14:24:51.118345	2025-01-16 14:24:51.118345	SYSTEM	SYSTEM	f
\.


--
-- TOC entry 3436 (class 0 OID 24991)
-- Dependencies: 219
-- Data for Name: t_vehicle_event_hist; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

COPY public.t_vehicle_event_hist (id, version, vehicle_id, co_event_type, ts_start_event_h, ts_end_event_h, lb_event, creation_date, update_date, created_by, updated_by, is_deleted) FROM stdin;
\.


--
-- TOC entry 3245 (class 2606 OID 24999)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 3274 (class 2606 OID 25001)
-- Name: t_monitoring_param_version param_api_version_pkey1; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_monitoring_param_version
    ADD CONSTRAINT param_api_version_pkey1 PRIMARY KEY (id);


--
-- TOC entry 3272 (class 2606 OID 25003)
-- Name: t_blob_storage pk_blob_storage; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_blob_storage
    ADD CONSTRAINT pk_blob_storage PRIMARY KEY (id);


--
-- TOC entry 3248 (class 2606 OID 25005)
-- Name: ref_calendar_billing pk_calendar_billing_ref; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_calendar_billing
    ADD CONSTRAINT pk_calendar_billing_ref PRIMARY KEY (id);


--
-- TOC entry 3252 (class 2606 OID 25007)
-- Name: ref_caution_billing pk_caution_billing_ref; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_caution_billing
    ADD CONSTRAINT pk_caution_billing_ref PRIMARY KEY (id);


--
-- TOC entry 3278 (class 2606 OID 25009)
-- Name: t_vehicle_event_hist pk_event_vehicle_ref; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_vehicle_event_hist
    ADD CONSTRAINT pk_event_vehicle_ref PRIMARY KEY (id);


--
-- TOC entry 3256 (class 2606 OID 25011)
-- Name: ref_gray_card pk_gray_card_ref; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_gray_card
    ADD CONSTRAINT pk_gray_card_ref PRIMARY KEY (id);


--
-- TOC entry 3262 (class 2606 OID 25013)
-- Name: ref_vehicle pk_vehicle_ref; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_vehicle
    ADD CONSTRAINT pk_vehicle_ref PRIMARY KEY (id);


--
-- TOC entry 3254 (class 2606 OID 25015)
-- Name: ref_endpoint ref_endpoint_pkey1; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_endpoint
    ADD CONSTRAINT ref_endpoint_pkey1 PRIMARY KEY (id);


--
-- TOC entry 3258 (class 2606 OID 25017)
-- Name: ref_gray_card ref_gray_card_uk; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_gray_card
    ADD CONSTRAINT ref_gray_card_uk UNIQUE (serial_operation_number);


--
-- TOC entry 3260 (class 2606 OID 25019)
-- Name: ref_module ref_module_pkey; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_module
    ADD CONSTRAINT ref_module_pkey PRIMARY KEY (id);


--
-- TOC entry 3270 (class 2606 OID 25021)
-- Name: ref_version ref_version_pkey1; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_version
    ADD CONSTRAINT ref_version_pkey1 PRIMARY KEY (id);


--
-- TOC entry 3264 (class 2606 OID 25023)
-- Name: ref_vehicle uk_chassis_number; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_vehicle
    ADD CONSTRAINT uk_chassis_number UNIQUE (serial_chassis_number);


--
-- TOC entry 3250 (class 2606 OID 25025)
-- Name: ref_calendar_billing uk_ref_calendar_billing; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_calendar_billing
    ADD CONSTRAINT uk_ref_calendar_billing UNIQUE (ref_year, ref_month, vehicle_id);


--
-- TOC entry 3266 (class 2606 OID 25027)
-- Name: ref_vehicle uk_registration_number; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_vehicle
    ADD CONSTRAINT uk_registration_number UNIQUE (registration_number);


--
-- TOC entry 3276 (class 2606 OID 25029)
-- Name: t_monitoring_param_version uk_version_endpoint; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_monitoring_param_version
    ADD CONSTRAINT uk_version_endpoint UNIQUE (ref_version_id, ref_endpoint_id);


--
-- TOC entry 3268 (class 2606 OID 25031)
-- Name: ref_vehicle uk_ww_registration_number; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_vehicle
    ADD CONSTRAINT uk_ww_registration_number UNIQUE (ww_registration_number);


--
-- TOC entry 3246 (class 1259 OID 25032)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: d_mdm_user
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 3279 (class 2606 OID 25033)
-- Name: ref_calendar_billing calendar_vehicle_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_calendar_billing
    ADD CONSTRAINT calendar_vehicle_fk FOREIGN KEY (vehicle_id) REFERENCES public.ref_vehicle(id);


--
-- TOC entry 3280 (class 2606 OID 25038)
-- Name: ref_caution_billing caution_vehicle_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_caution_billing
    ADD CONSTRAINT caution_vehicle_fk FOREIGN KEY (vehicle_id) REFERENCES public.ref_vehicle(id);


--
-- TOC entry 3286 (class 2606 OID 25043)
-- Name: t_vehicle_event_hist event_vehicle_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_vehicle_event_hist
    ADD CONSTRAINT event_vehicle_fk FOREIGN KEY (vehicle_id) REFERENCES public.ref_vehicle(id);


--
-- TOC entry 3281 (class 2606 OID 25048)
-- Name: ref_endpoint fk_module_endpoint_id; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_endpoint
    ADD CONSTRAINT fk_module_endpoint_id FOREIGN KEY (module_id) REFERENCES public.ref_module(id);


--
-- TOC entry 3283 (class 2606 OID 25053)
-- Name: ref_version fk_module_version_id; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_version
    ADD CONSTRAINT fk_module_version_id FOREIGN KEY (module_id) REFERENCES public.ref_module(id);


--
-- TOC entry 3284 (class 2606 OID 25058)
-- Name: t_monitoring_param_version fk_param_ref_endpoint_id; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_monitoring_param_version
    ADD CONSTRAINT fk_param_ref_endpoint_id FOREIGN KEY (ref_endpoint_id) REFERENCES public.ref_endpoint(id);


--
-- TOC entry 3285 (class 2606 OID 25063)
-- Name: t_monitoring_param_version fk_param_ref_version_id; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_monitoring_param_version
    ADD CONSTRAINT fk_param_ref_version_id FOREIGN KEY (ref_version_id) REFERENCES public.ref_version(id);


--
-- TOC entry 3282 (class 2606 OID 25068)
-- Name: ref_gray_card gray_card_vehicle_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_gray_card
    ADD CONSTRAINT gray_card_vehicle_fk FOREIGN KEY (vehicle_id) REFERENCES public.ref_vehicle(id);


-- Completed on 2025-01-21 13:47:24

--
-- PostgreSQL database dump complete
--

