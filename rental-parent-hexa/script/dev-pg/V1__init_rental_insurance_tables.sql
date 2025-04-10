-- table t_insurance_company
CREATE TABLE IF NOT exists t_insurance_company (
  	id uuid NOT NULL,
  	version integer NOT NULL,

  	company_name varchar(50) NOT NULL,
  	company_ref varchar(50) NOT NULL,
  	address text DEFAULT NULL,
  	email varchar(100) NOT NULL,
  	phone_number varchar(20) NOT NULL,

    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,

    CONSTRAINT PK_INSURANCE_COMPANY PRIMARY KEY (id)
);

ALTER TABLE t_insurance_company ADD CONSTRAINT uk_company_name UNIQUE (company_name);
ALTER TABLE t_insurance_company ADD CONSTRAINT uk_company_ref UNIQUE (company_ref);


-- table t_insurance_owner
CREATE TABLE IF NOT exists t_insurance_owner (
  	id uuid NOT NULL,
  	version integer NOT NULL,

  	full_name varchar(50) NOT NULL,
  	address text DEFAULT NULL,
  	owner_identifier varchar(50) NOT NULL,

    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,

    CONSTRAINT PK_INSURANCE_OWNER PRIMARY KEY (id)
);

-- table t_insurance_vehicle
CREATE TABLE IF NOT exists t_insurance_vehicle (
  	id uuid NOT NULL,
  	version integer NOT NULL,

  	no_insurance_policy varchar(50) NOT NULL,
  	vehicle_identifier uuid NOT NULL,
  	v_registration_number varchar(50) NOT NULL,
  	ld_start_insure timestamp without time zone NOT NULL,
  	ld_end_insure timestamp without time zone NOT NULL,
  	ld_renewal_insure timestamp without time zone NOT NULL,

  	mt_annual_cost NUMERIC(10,2) NOT NULL,
  	mt_annual_cost_with_tax NUMERIC(10,2) NOT NULL,

    insurance_company_id uuid NOT NULL,

    insurance_owner_id uuid NOT NULL,

    is_covered bool DEFAULT true,
    is_expired bool DEFAULT false,

    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,

    CONSTRAINT PK_INSURANCE_VEHICLE PRIMARY KEY (id),
    CONSTRAINT INSURANCE_COMPANY_FK FOREIGN KEY (insurance_company_id) REFERENCES t_insurance_company(id),
    CONSTRAINT INSURANCE_OWNER_FK FOREIGN KEY (insurance_owner_id) REFERENCES t_insurance_owner(id)

);

-- table t_insurance_coverage
CREATE TABLE IF NOT exists t_insurance_coverage (
  	id uuid NOT NULL,
  	version integer NOT NULL,

    insurance_vehicle_id uuid NOT NULL,
    co_coverage_type varchar(30) NOT NULL,
    lb_description varchar(255) DEFAULT NULL,
  	mt_coverage_limit NUMERIC(10,2) NOT NULL,
  	mt_franchise NUMERIC(10,2) NOT NULL,

    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,

    CONSTRAINT PK_INSURANCE_COVERAGE PRIMARY KEY (id),
    CONSTRAINT INSURANCE_VEHICLE_FK FOREIGN KEY (insurance_vehicle_id) REFERENCES t_insurance_vehicle(id)

);

-- table t_insurance_sinister
CREATE TABLE IF NOT exists t_insurance_sinister (
  	id uuid NOT NULL,
  	version integer NOT NULL,

    insurance_vehicle_id uuid NOT NULL,
  	no_insurance_policy varchar(50) NOT NULL,
    co_sinister_status varchar(20) NOT NULL,
    co_sinister_type varchar(50) NOT NULL,
    ld_sinister_date timestamp without time zone NOT NULL,
  	vehicle_identifier uuid NOT NULL,
    lb_description varchar(255) DEFAULT NULL,

  	mt_estimated_cost NUMERIC(10,2) NOT NULL,
  	mt_estimated_cost_with_tax NUMERIC(10,2) NOT NULL,
  	mt_real_cost NUMERIC(10,2) NOT NULL,
  	mt_real_cost_with_tax NUMERIC(10,2) NOT NULL,

    lb_customer_identifier varchar(30) NOT NULL,
    lb_customer_name varchar(50) NOT NULL,

    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,

    CONSTRAINT PK_INSURANCE_SINISTER PRIMARY KEY (id)
);

-- table t_insurance_sinister_event
CREATE TABLE IF NOT exists t_insurance_sinister_event (
  	id uuid NOT NULL,
  	version integer NOT NULL,

    insurance_sinister_id uuid NOT NULL,
    co_sinister_type varchar(50) NOT NULL,
    ld_start_event timestamp without time zone NOT NULL,
    ld_end_event timestamp without time zone DEFAULT NULL,

    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,

    CONSTRAINT PK_INSURANCE_SINISTER_EVENT PRIMARY KEY (id),
    CONSTRAINT INSURANCE_SINISTER_FK FOREIGN KEY (insurance_sinister_id) REFERENCES t_insurance_sinister(id)

);


