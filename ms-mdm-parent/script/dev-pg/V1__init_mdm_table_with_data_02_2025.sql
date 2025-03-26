--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

-- Started on 2025-02-20 15:14:51

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 17808)
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
-- TOC entry 215 (class 1259 OID 17795)
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
-- TOC entry 219 (class 1259 OID 17848)
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
-- TOC entry 214 (class 1259 OID 17780)
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
-- TOC entry 212 (class 1259 OID 17725)
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
-- TOC entry 213 (class 1259 OID 17762)
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
-- TOC entry 210 (class 1259 OID 17674)
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
-- TOC entry 218 (class 1259 OID 17837)
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
-- TOC entry 211 (class 1259 OID 17693)
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
-- TOC entry 217 (class 1259 OID 17823)
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
-- TOC entry 3417 (class 0 OID 17808)
-- Dependencies: 216
-- Data for Name: ref_calendar_billing; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--



--
-- TOC entry 3416 (class 0 OID 17795)
-- Dependencies: 215
-- Data for Name: ref_caution_billing; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--



--
-- TOC entry 3420 (class 0 OID 17848)
-- Dependencies: 219
-- Data for Name: ref_endpoint; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

INSERT INTO public.ref_endpoint VALUES ('578cc0df-855a-4dd6-8b00-506d3f12786d', 0, 'auth', '/api/token/refresh', '6d93e5c0-0749-4456-b362-2f8d53dba7b5', true, '2025-02-19 21:18:07.291662', '2025-02-19 21:18:07.324771', '2025-02-19 21:18:07.324672', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.ref_endpoint VALUES ('11be6fe1-40ad-475c-8829-fb71926b0eeb', 0, 'auth', '/api/login', '6d93e5c0-0749-4456-b362-2f8d53dba7b5', true, '2025-02-19 21:18:07.290843', '2025-02-19 21:18:07.327188', '2025-02-19 21:18:07.32677', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.ref_endpoint VALUES ('38e22ed9-beb4-45da-86fa-c57e98526133', 0, 'auth', '/api/fetch/public-key', '6d93e5c0-0749-4456-b362-2f8d53dba7b5', true, '2025-02-19 21:18:07.291281', '2025-02-19 21:18:07.330013', '2025-02-19 21:18:07.329787', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.ref_endpoint VALUES ('ea9023a9-1153-4a44-b1f4-dfe7de721b1f', 0, 'auth', '/api/user/[a-z0-9]+/activate/[a-f0-9]{32}', '6d93e5c0-0749-4456-b362-2f8d53dba7b5', true, '2025-02-19 21:18:07.291681', '2025-02-19 21:18:07.331431', '2025-02-19 21:18:07.331251', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.ref_endpoint VALUES ('e8782655-1967-4456-ab68-01e50192b324', 0, 'auth', '/api/user/register', '6d93e5c0-0749-4456-b362-2f8d53dba7b5', true, '2025-02-19 21:18:07.291644', '2025-02-19 21:18:07.332798', '2025-02-19 21:18:07.332637', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.ref_endpoint VALUES ('49c5cf93-e484-4a3d-897f-91d4582429e0', 0, 'auth', '/api/token/info/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}', '6d93e5c0-0749-4456-b362-2f8d53dba7b5', true, '2025-02-19 21:18:07.291672', '2025-02-19 21:18:07.33583', '2025-02-19 21:18:07.335718', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.ref_endpoint VALUES ('9eaa2ab9-2d93-4b18-8130-b6b535ae1e5c', 0, 'rental', '/api/insurance-vehicle/[a-z]+-[0-9]+-[a-z0-9]+/apply-coverage', 'bb3eb91e-d533-4b49-9ce6-64db6893a86a', true, '2025-02-20 15:13:54.414719', '2025-02-20 15:13:54.432243', '2025-02-20 15:13:54.432243', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.ref_endpoint VALUES ('b5f78ae3-b4d4-4aea-905c-47651cbeddc4', 0, 'rental', '/api/insurance-vehicle/create', 'bb3eb91e-d533-4b49-9ce6-64db6893a86a', true, '2025-02-20 15:13:54.414719', '2025-02-20 15:13:54.432243', '2025-02-20 15:13:54.432243', 'SYSTEM', 'SYSTEM', false);


--
-- TOC entry 3415 (class 0 OID 17780)
-- Dependencies: 214
-- Data for Name: ref_gray_card; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--



--
-- TOC entry 3413 (class 0 OID 17725)
-- Dependencies: 212
-- Data for Name: ref_module; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

INSERT INTO public.ref_module VALUES ('6d93e5c0-0749-4456-b362-2f8d53dba7b5', 0, 'AUTH', 'auth', 'Authentication Service Provider', '2025-02-19 21:18:07.155748', '2025-02-19 21:18:07.155561', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.ref_module VALUES ('bb3eb91e-d533-4b49-9ce6-64db6893a86a', 0, 'RENTAL', 'rental', 'The Rental Service Provider', '2025-02-20 15:13:54.348922', '2025-02-20 15:13:54.348922', 'SYSTEM', 'SYSTEM', false);


--
-- TOC entry 3414 (class 0 OID 17762)
-- Dependencies: 213
-- Data for Name: ref_vehicle; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--



--
-- TOC entry 3411 (class 0 OID 17674)
-- Dependencies: 210
-- Data for Name: ref_version; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

INSERT INTO public.ref_version VALUES ('3f5f3f42-cebe-4eef-8a6f-7a82b876b33a', 0, '1.0', '6d93e5c0-0749-4456-b362-2f8d53dba7b5', true, '2025-02-19 21:18:07.303502', '2025-02-19 21:18:07.348518', '2025-02-19 21:18:07.348376', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.ref_version VALUES ('90705f74-7348-4ba8-b4b3-cdadf51db874', 0, '1.0', 'bb3eb91e-d533-4b49-9ce6-64db6893a86a', true, '2025-02-20 15:13:54.414719', '2025-02-20 15:13:54.447368', '2025-02-20 15:13:54.447368', 'SYSTEM', 'SYSTEM', false);


--
-- TOC entry 3419 (class 0 OID 17837)
-- Dependencies: 218
-- Data for Name: t_blob_storage; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--



--
-- TOC entry 3412 (class 0 OID 17693)
-- Dependencies: 211
-- Data for Name: t_monitoring_param_version; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--

INSERT INTO public.t_monitoring_param_version VALUES ('babf8bcf-2607-4a86-924a-4261ec5912bc', 0, '3f5f3f42-cebe-4eef-8a6f-7a82b876b33a', '578cc0df-855a-4dd6-8b00-506d3f12786d', true, false, NULL, '2025-02-19 21:18:08.49284', '2025-02-19 21:18:08.492453', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.t_monitoring_param_version VALUES ('a50e90e2-2730-4bf8-b912-b5ef5707db86', 0, '3f5f3f42-cebe-4eef-8a6f-7a82b876b33a', '11be6fe1-40ad-475c-8829-fb71926b0eeb', true, false, NULL, '2025-02-19 21:18:08.496304', '2025-02-19 21:18:08.495516', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.t_monitoring_param_version VALUES ('8a29c2f7-e91e-4c7b-98c8-5b20c988630d', 0, '3f5f3f42-cebe-4eef-8a6f-7a82b876b33a', '38e22ed9-beb4-45da-86fa-c57e98526133', true, false, NULL, '2025-02-19 21:18:08.497783', '2025-02-19 21:18:08.497714', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.t_monitoring_param_version VALUES ('c3566719-6966-4497-826e-c0debec4d9b7', 0, '3f5f3f42-cebe-4eef-8a6f-7a82b876b33a', 'ea9023a9-1153-4a44-b1f4-dfe7de721b1f', true, false, NULL, '2025-02-19 21:18:08.498705', '2025-02-19 21:18:08.498652', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.t_monitoring_param_version VALUES ('94708df8-dbac-4a67-9ce7-b038cd03e67e', 0, '3f5f3f42-cebe-4eef-8a6f-7a82b876b33a', 'e8782655-1967-4456-ab68-01e50192b324', true, false, NULL, '2025-02-19 21:18:08.499696', '2025-02-19 21:18:08.499615', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.t_monitoring_param_version VALUES ('41ca1186-7939-4eb9-99ef-811477af2480', 0, '3f5f3f42-cebe-4eef-8a6f-7a82b876b33a', '49c5cf93-e484-4a3d-897f-91d4582429e0', true, false, NULL, '2025-02-19 21:18:08.500476', '2025-02-19 21:18:08.500422', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.t_monitoring_param_version VALUES ('fc2ed192-c714-431f-94b3-68a9b5cb7c69', 0, '90705f74-7348-4ba8-b4b3-cdadf51db874', '9eaa2ab9-2d93-4b18-8130-b6b535ae1e5c', true, false, NULL, '2025-02-20 15:13:54.997394', '2025-02-20 15:13:54.997394', 'SYSTEM', 'SYSTEM', false);
INSERT INTO public.t_monitoring_param_version VALUES ('45594883-e884-4716-be17-502bea783609', 0, '90705f74-7348-4ba8-b4b3-cdadf51db874', 'b5f78ae3-b4d4-4aea-905c-47651cbeddc4', true, false, NULL, '2025-02-20 15:13:54.997394', '2025-02-20 15:13:54.997394', 'SYSTEM', 'SYSTEM', false);


--
-- TOC entry 3418 (class 0 OID 17823)
-- Dependencies: 217
-- Data for Name: t_vehicle_event_hist; Type: TABLE DATA; Schema: public; Owner: d_mdm_user
--



--
-- TOC entry 3235 (class 2606 OID 17702)
-- Name: t_monitoring_param_version param_api_version_pkey1; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_monitoring_param_version
    ADD CONSTRAINT param_api_version_pkey1 PRIMARY KEY (id);


--
-- TOC entry 3261 (class 2606 OID 17847)
-- Name: t_blob_storage pk_blob_storage; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_blob_storage
    ADD CONSTRAINT pk_blob_storage PRIMARY KEY (id);


--
-- TOC entry 3255 (class 2606 OID 17815)
-- Name: ref_calendar_billing pk_calendar_billing_ref; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_calendar_billing
    ADD CONSTRAINT pk_calendar_billing_ref PRIMARY KEY (id);


--
-- TOC entry 3253 (class 2606 OID 17802)
-- Name: ref_caution_billing pk_caution_billing_ref; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_caution_billing
    ADD CONSTRAINT pk_caution_billing_ref PRIMARY KEY (id);


--
-- TOC entry 3259 (class 2606 OID 17831)
-- Name: t_vehicle_event_hist pk_event_vehicle_ref; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_vehicle_event_hist
    ADD CONSTRAINT pk_event_vehicle_ref PRIMARY KEY (id);


--
-- TOC entry 3249 (class 2606 OID 17787)
-- Name: ref_gray_card pk_gray_card_ref; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_gray_card
    ADD CONSTRAINT pk_gray_card_ref PRIMARY KEY (id);


--
-- TOC entry 3241 (class 2606 OID 17773)
-- Name: ref_vehicle pk_vehicle_ref; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_vehicle
    ADD CONSTRAINT pk_vehicle_ref PRIMARY KEY (id);


--
-- TOC entry 3263 (class 2606 OID 17856)
-- Name: ref_endpoint ref_endpoint_pkey1; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_endpoint
    ADD CONSTRAINT ref_endpoint_pkey1 PRIMARY KEY (id);


--
-- TOC entry 3251 (class 2606 OID 17794)
-- Name: ref_gray_card ref_gray_card_uk; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_gray_card
    ADD CONSTRAINT ref_gray_card_uk UNIQUE (serial_operation_number);


--
-- TOC entry 3239 (class 2606 OID 17732)
-- Name: ref_module ref_module_pkey; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_module
    ADD CONSTRAINT ref_module_pkey PRIMARY KEY (id);


--
-- TOC entry 3233 (class 2606 OID 17682)
-- Name: ref_version ref_version_pkey1; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_version
    ADD CONSTRAINT ref_version_pkey1 PRIMARY KEY (id);


--
-- TOC entry 3243 (class 2606 OID 17779)
-- Name: ref_vehicle uk_chassis_number; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_vehicle
    ADD CONSTRAINT uk_chassis_number UNIQUE (serial_chassis_number);


--
-- TOC entry 3257 (class 2606 OID 17822)
-- Name: ref_calendar_billing uk_ref_calendar_billing; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_calendar_billing
    ADD CONSTRAINT uk_ref_calendar_billing UNIQUE (ref_year, ref_month, vehicle_id);


--
-- TOC entry 3245 (class 2606 OID 17775)
-- Name: ref_vehicle uk_registration_number; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_vehicle
    ADD CONSTRAINT uk_registration_number UNIQUE (registration_number);


--
-- TOC entry 3237 (class 2606 OID 17704)
-- Name: t_monitoring_param_version uk_version_endpoint; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_monitoring_param_version
    ADD CONSTRAINT uk_version_endpoint UNIQUE (ref_version_id, ref_endpoint_id);


--
-- TOC entry 3247 (class 2606 OID 17777)
-- Name: ref_vehicle uk_ww_registration_number; Type: CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_vehicle
    ADD CONSTRAINT uk_ww_registration_number UNIQUE (ww_registration_number);


--
-- TOC entry 3269 (class 2606 OID 17816)
-- Name: ref_calendar_billing calendar_vehicle_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_calendar_billing
    ADD CONSTRAINT calendar_vehicle_fk FOREIGN KEY (vehicle_id) REFERENCES public.ref_vehicle(id);


--
-- TOC entry 3268 (class 2606 OID 17803)
-- Name: ref_caution_billing caution_vehicle_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_caution_billing
    ADD CONSTRAINT caution_vehicle_fk FOREIGN KEY (vehicle_id) REFERENCES public.ref_vehicle(id);


--
-- TOC entry 3270 (class 2606 OID 17832)
-- Name: t_vehicle_event_hist event_vehicle_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_vehicle_event_hist
    ADD CONSTRAINT event_vehicle_fk FOREIGN KEY (vehicle_id) REFERENCES public.ref_vehicle(id);


--
-- TOC entry 3271 (class 2606 OID 17857)
-- Name: ref_endpoint fk_module_endpoint_id; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_endpoint
    ADD CONSTRAINT fk_module_endpoint_id FOREIGN KEY (module_id) REFERENCES public.ref_module(id);


--
-- TOC entry 3264 (class 2606 OID 17738)
-- Name: ref_version fk_module_version_id; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_version
    ADD CONSTRAINT fk_module_version_id FOREIGN KEY (module_id) REFERENCES public.ref_module(id);


--
-- TOC entry 3266 (class 2606 OID 17862)
-- Name: t_monitoring_param_version fk_param_ref_endpoint_id; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_monitoring_param_version
    ADD CONSTRAINT fk_param_ref_endpoint_id FOREIGN KEY (ref_endpoint_id) REFERENCES public.ref_endpoint(id);


--
-- TOC entry 3265 (class 2606 OID 17705)
-- Name: t_monitoring_param_version fk_param_ref_version_id; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.t_monitoring_param_version
    ADD CONSTRAINT fk_param_ref_version_id FOREIGN KEY (ref_version_id) REFERENCES public.ref_version(id);


--
-- TOC entry 3267 (class 2606 OID 17788)
-- Name: ref_gray_card gray_card_vehicle_fk; Type: FK CONSTRAINT; Schema: public; Owner: d_mdm_user
--

ALTER TABLE ONLY public.ref_gray_card
    ADD CONSTRAINT gray_card_vehicle_fk FOREIGN KEY (vehicle_id) REFERENCES public.ref_vehicle(id);


-- Completed on 2025-02-20 15:14:53

--
-- PostgreSQL database dump complete
--

