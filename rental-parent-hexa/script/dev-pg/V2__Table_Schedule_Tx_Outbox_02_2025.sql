-- table t_schedule_tx_outbox
CREATE TABLE IF NOT exists t_schedule_task_tx_outbox (
  	id uuid NOT NULL,

  	entity_id uuid NOT NULL,
  	entity_name varchar(70) NOT NULL,
  	co_action_tx varchar(15) NOT NULL,
    creation_date timestamp without time zone DEFAULT NULL,
  	payload_content text NOT NULL,
  	co_kafka_key varchar(100) NOT NULL,
  	publish_group_delivery text NOT NULL,
    in_processed boolean DEFAULT FALSE NOT NULL,
    class_invoke_name varchar(255) NOT NULL,
    method_invoke_name varchar(100) NOT NULL,
    err_message text DEFAULT NULL,
    class_exception_name varchar(100) DEFAULT NULL,
    trace_id varchar(20) NOT NULL,
    span_id varchar(20) NOT NULL,

    CONSTRAINT pk_schedule_tx_outbox PRIMARY KEY (id)
);