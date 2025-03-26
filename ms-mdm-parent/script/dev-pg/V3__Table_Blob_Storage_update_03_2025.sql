-- Drop table t_blob_storage
DROP TABLE public.t_blob_storage;

-- create table t_blob_storage
CREATE TABLE public.t_blob_storage (
    id uuid NOT NULL,
    version integer NOT NULL,
    co_document_type character varying(30) NOT NULL,
    document_name character varying(50) NOT NULL,
    document_extension character varying(10) NOT NULL,
    id_object uuid NOT NULL,
    ref_object character varying(50) DEFAULT NULL::character varying,
    object_name character varying(50) NOT NULL,
    object_domain character varying(50) NOT NULL,
    blob_content bytea NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone,
    created_by character varying(50) DEFAULT NULL::character varying,
    updated_by character varying(50) DEFAULT NULL::character varying,
    is_deleted boolean DEFAULT false
);
