-- table t_inspection_vehicle
CREATE TABLE IF NOT exists t_inspection_vehicle (
  	id uuid NOT NULL,
  	version integer NOT NULL,

  	no_inspection varchar(50) NOT NULL,
  	id_vehicle uuid NOT NULL,
  	rn_vehicle varchar(50) NOT NULL,
  	id_blob_storage uuid NOT NULL,
  	ld_inspection_date timestamp without time zone NOT NULL,
  	ld_next_inspection_date timestamp without time zone DEFAULT NULL,
    inspection_mileage integer NOT NULL,
    co_inspection_result varchar(30) NOT NULL,
    inspection_center varchar(50) NOT NULL,
    inspection_address text DEFAULT NULL,
    inspection_phone varchar(20) DEFAULT NULL,
    inspection_comment text DEFAULT NULL,
  	mt_inspection NUMERIC(10,2) NOT NULL,
  	mt_purchase_with_vat NUMERIC(10,2) NOT NULL,

    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,

    CONSTRAINT PK_INSPECTION_VEHICLE PRIMARY KEY (id)
);

-- table t_tsav_vehicle
CREATE TABLE IF NOT exists t_tsav_vehicle (
  	id uuid NOT NULL,
  	version integer NOT NULL,

  	no_tsav varchar(50) NOT NULL,
    id_vehicle uuid NOT NULL,
    rn_vehicle varchar(50) NOT NULL,
    id_blob_storage uuid NOT NULL,
  	ld_tsav_date timestamp without time zone NOT NULL,
  	ld_next_tsav_date timestamp without time zone DEFAULT NULL,
    tsav_comment text DEFAULT NULL,
  	mt_tsav NUMERIC(10,2) NOT NULL,
  	mt_tsav_with_vat NUMERIC(10,2) NOT NULL,

    creation_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone DEFAULT NULL,
    created_by varchar(50) DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,

    CONSTRAINT PK_TSAV_VEHICLE PRIMARY KEY (id)
);