-- table t_audit_event
CREATE TABLE IF NOT exists t_audit_event (

  	id uuid PRIMARY KEY,
  	version integer NOT NULL,

  	co_audit_type varchar(10) NOT NULL,
  	co_module varchar(10) NOT NULL,
  	sys_action varchar(10) NOT NULL,
    lb_audit_creation timestamp without time zone NOT NULL,
  	group_header varchar(50) NOT NULL,
  	message_key varchar(50) NOT NULL,
  	no_partition integer NOT NULL,
  	no_offset integer NOT NULL,
  	class_of_message varchar(100) NOT NULL,
  	class_name varchar(100) NOT NULL,
  	method_name varchar(50) NOT NULL,

    -- audit-type = trace
  	metadata text DEFAULT NULL,

    -- audit-type = query
  	entity_name varchar(30) DEFAULT NULL,
  	entity_identifier varchar(50) DEFAULT NULL,
  	co_query_type varchar(20) DEFAULT NULL,
  	query_value text DEFAULT NULL,
  	property_names text DEFAULT NULL,
  	property_values text DEFAULT NULL,
  	property_change_values text DEFAULT NULL,

  	trace_id varchar(50) NOT NULL,
  	span_id varchar(50) NOT NULL,

    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL

);

-- table t_audit_error_event
CREATE TABLE IF NOT exists t_audit_error_event (

  	id uuid PRIMARY KEY,
  	version integer NOT NULL,

    audit_id uuid NOT NULL,
  	co_error_type varchar(10) NOT NULL,
  	error_message text NOT NULL,
  	class_exception_name text NOT NULL,

    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    CONSTRAINT fk_audit_err_event_id FOREIGN KEY (audit_id) REFERENCES t_audit_event(id)

);