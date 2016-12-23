# Tasks schema
 
# --- !Ups

CREATE SEQUENCE task_id_seq;
CREATE TABLE task (
    id integer NOT NULL DEFAULT nextval('task_id_seq'),
    label varchar(255),
	email varchar(255) NOT NULL,
	phone varchar(255) NOT NULL,
	address varchar(255) NOT NULL,
	sex varchar(255) NOT NULL,
	age Int NOT NULL
);


# --- !Downs
 
DROP TABLE task;
DROP SEQUENCE task_id_seq;