DROP TABLE IF EXISTS insertajax_request;
CREATE TABLE insertajax_request (
  id_insertajax_request INT DEFAULT '0' NOT NULL,
  name varchar(255) DEFAULT NULL,
  html LONG VARCHAR DEFAULT NULL,
  sqlrequest varchar(255) DEFAULT NULL,
  workgroup_key varchar(50) DEFAULT 'all' NOT NULL,  
  role varchar(50) DEFAULT 'none' NOT NULL,  
  date_update timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY  (id_insertajax_request)
);