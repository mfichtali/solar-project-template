-- table param_api_version
CREATE TABLE IF NOT exists param_api_version (
  	id uuid PRIMARY KEY,
  	version integer NOT NULL,
  	ref_version_id uuid NOT NULL,
  	ref_endpoint_id uuid NOT NULL,
  	is_active boolean DEFAULT TRUE,
  	is_lock boolean DEFAULT FALSE,
  	creation_date timestamp without time zone DEFAULT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    is_deleted boolean DEFAULT FALSE,
  CONSTRAINT uk_version_endpoint UNIQUE (ref_version_id, ref_endpoint_id),
  CONSTRAINT fk_param_ref_version_id FOREIGN KEY (ref_version_id) REFERENCES ref_version(id),
  CONSTRAINT fk_param_ref_endpoint_id FOREIGN KEY (ref_endpoint_id) REFERENCES ref_endpoint(id)
);

-- drop not null label column
ALTER TABLE ref_module ALTER COLUMN label DROP NOT NULL;
