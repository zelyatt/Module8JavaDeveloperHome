

CREATE TABLE customers (
                           id         BIGINT DEFAULT nextval('seq_customer_id') PRIMARY KEY,
                           first_name VARCHAR(255),
                           last_name  VARCHAR(255) NOT NULL,
                           email      VARCHAR(50) UNIQUE,
                           tel_number VARCHAR(50),
                           post_code  VARCHAR(10)
);
DROP TABLE IF EXISTS customers;
CREATE  SEQUENCE seq_customer_id
    START WITH 1
    INCREMENT BY 1;
