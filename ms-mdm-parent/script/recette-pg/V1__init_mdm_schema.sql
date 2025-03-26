-- table ref_module
CREATE TABLE IF NOT exists ref_module (
  	id uuid NOT NULL,
  	version integer NOT NULL,
  	code varchar(20) NOT NULL,
  	label varchar(50) NOT NULL,
  	creation_date  timestamp without time zone DEFAULT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    is_deleted boolean DEFAULT FALSE,
  CONSTRAINT pk_module PRIMARY KEY (id)
);

-- table ref_endpoint
CREATE TABLE IF NOT exists ref_endpoint (
  	id uuid NOT NULL,
  	version integer NOT NULL,
  	path_endpoint varchar(200) NOT NULL,
  	module_id uuid NOT NULL,
  	creation_date  timestamp without time zone DEFAULT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    is_deleted boolean DEFAULT FALSE,
  CONSTRAINT pk_endpoint PRIMARY KEY (id),
  CONSTRAINT fk_module_enpoint_id FOREIGN KEY (module_id) REFERENCES ref_module(id)
);

-- table ref_version
CREATE TABLE IF NOT exists ref_version (
  	id uuid NOT NULL,
  	version integer NOT NULL,
  	version_number varchar(20) NOT NULL,
  	module_id uuid NOT NULL,
  	creation_date  timestamp without time zone DEFAULT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    is_deleted boolean DEFAULT FALSE,
  CONSTRAINT pk_version_ref PRIMARY KEY (id),
  CONSTRAINT fk_module_version_id FOREIGN KEY (module_id) REFERENCES ref_module(id)
);