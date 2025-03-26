-- Rename table param_api_version
ALTER TABLE param_api_version RENAME TO param_api_version_snap;

ALTER TABLE param_api_version_snap DROP CONSTRAINT uk_version_endpoint;
ALTER TABLE param_api_version_snap DROP CONSTRAINT fk_param_ref_version_id;
ALTER TABLE param_api_version_snap DROP CONSTRAINT fk_param_ref_endpoint_id;

-- table param_api_version
CREATE TABLE IF NOT exists param_api_version (
  	id uuid PRIMARY KEY,
  	version integer NOT NULL,
  	ref_version_id uuid NOT NULL,
  	ref_endpoint_id uuid NOT NULL,
  	is_active boolean DEFAULT TRUE,
  	is_lock boolean DEFAULT FALSE,
  	date_lock timestamp without time zone DEFAULT NULL,
  	creation_date timestamp without time zone DEFAULT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    is_deleted boolean DEFAULT FALSE,
  CONSTRAINT uk_version_endpoint UNIQUE (ref_version_id, ref_endpoint_id),
  CONSTRAINT fk_param_ref_version_id FOREIGN KEY (ref_version_id) REFERENCES ref_version(id),
  CONSTRAINT fk_param_ref_endpoint_id FOREIGN KEY (ref_endpoint_id) REFERENCES ref_endpoint(id)
);

-- migrate data where is_lock = false
insert into param_api_version (id, version, ref_version_id, ref_endpoint_id, is_active, is_lock, creation_date, update_date, created_by, updated_by, is_deleted) 
select id, version, ref_version_id, ref_endpoint_id, is_active, is_lock, creation_date, update_date, created_by, updated_by, is_deleted from param_api_version_snap where is_lock = false;

-- migrate data where is_lock = true
insert into param_api_version (id, version, ref_version_id, ref_endpoint_id, is_active, is_lock, date_lock, creation_date, update_date, created_by, updated_by, is_deleted) 
select id, version, ref_version_id, ref_endpoint_id, is_active, is_lock, creation_date, creation_date, update_date, created_by, updated_by, is_deleted from param_api_version_snap where is_lock = true;

-- Drop table param_api_version_snap
DROP TABLE IF EXISTS param_api_version_snap;