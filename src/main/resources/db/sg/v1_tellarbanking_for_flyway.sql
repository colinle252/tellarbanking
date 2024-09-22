-- Table: credit.company

-- DROP TABLE IF EXISTS credit.company;

CREATE TABLE IF NOT EXISTS credit.company
(
    id uuid NOT NULL,
    company_id character varying(255) COLLATE pg_catalog."default",
    company_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT company_pkey PRIMARY KEY (id),
    CONSTRAINT ukq79ooswnnmx4kafhpltwr6441 UNIQUE (company_id)
)

-- Table: credit.employee

-- DROP TABLE IF EXISTS credit.employee;

CREATE TABLE IF NOT EXISTS credit.employee
(
    id uuid NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    employee_id character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    company_id uuid,
    CONSTRAINT employee_pkey PRIMARY KEY (id),
    CONSTRAINT ukmc5x07dj0uft9opsxchp0uwji UNIQUE (employee_id),
    CONSTRAINT fk5v50ed2bjh60n1gc7ifuxmgf4 FOREIGN KEY (company_id)
        REFERENCES credit.company (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

-- Table: credit.account

-- DROP TABLE IF EXISTS credit.account;

CREATE TABLE IF NOT EXISTS credit.account
(
    id uuid NOT NULL,
    account_id character varying(255) COLLATE pg_catalog."default",
    balance numeric(38,2),
    employee_id uuid NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT uk4lde57f579n315au55ua4gqxk UNIQUE (account_id),
    CONSTRAINT fk1kec5bwba2rl0j8garlarwe3d FOREIGN KEY (employee_id)
        REFERENCES credit.employee (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

-- Table: credit.transaction

-- DROP TABLE IF EXISTS credit.transaction;

CREATE TABLE IF NOT EXISTS credit.transaction
(
    id uuid NOT NULL,
    amount numeric(38,2),
    transaction_id bigint,
    transaction_type character varying(255) COLLATE pg_catalog."default",
    account_id uuid NOT NULL,
    CONSTRAINT transaction_pkey PRIMARY KEY (id),
    CONSTRAINT uknevcwmpu8hb3a1naph2fyvqyu UNIQUE (transaction_id),
    CONSTRAINT fk6g20fcr3bhr6bihgy24rq1r1b FOREIGN KEY (account_id)
        REFERENCES credit.account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)