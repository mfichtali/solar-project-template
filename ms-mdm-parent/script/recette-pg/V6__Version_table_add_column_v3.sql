-- Rename table ref_endpoint
ALTER TABLE ref_version RENAME TO ref_version_snap;

-- table ref_version
CREATE TABLE IF NOT exists ref_version (
  	id uuid PRIMARY KEY,  
  	version integer NOT NULL,
  	version_number varchar(20) NOT NULL,
  	module_id uuid NOT NULL,
  	is_active boolean NOT NULL DEFAULT TRUE,
  	date_activation timestamp without time zone DEFAULT NULL,
  	creation_date  timestamp without time zone DEFAULT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    is_deleted boolean NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_module_version_id FOREIGN KEY (module_id) REFERENCES ref_module(id)
);

-- migrate data where is_active = false
insert into ref_version (id, version, version_number, module_id, is_active, creation_date, update_date, created_by, updated_by, is_deleted) 
select id, version, version_number, module_id, is_active, creation_date, update_date, created_by, updated_by, is_deleted from ref_version_snap where is_active = false;;

-- migrate data where is_active = true
insert into ref_version (id, version, version_number, module_id, is_active, date_activation, creation_date, update_date, created_by, updated_by, is_deleted) 
select id, version, version_number, module_id, is_active, creation_date, creation_date, update_date, created_by, updated_by, is_deleted from ref_version_snap where is_active = true;

-- recreate FK
ALTER TABLE param_api_version DROP CONSTRAINT fk_param_ref_version_id;
ALTER TABLE param_api_version ADD CONSTRAINT fk_param_ref_version_id FOREIGN KEY (ref_version_id) REFERENCES ref_version(id);
DROP TABLE IF EXISTS ref_version_snap;