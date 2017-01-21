--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: file; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE file (
  id BIGINT NOT NULL,
  data bytea,
  tenant_id BIGINT NOT NULL
);

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; 
--

DROP SEQUENCE IF EXISTS hibernate_sequence;

CREATE SEQUENCE hibernate_sequence
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

--
-- Name: property; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE property (
  id BIGINT NOT NULL,
  propertykey TEXT,
  propertyvalue TEXT,
  tenant_id BIGINT NOT NULL
);

--
-- Name: user; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE "user" (
  id BIGINT NOT NULL,
  emailaddress TEXT,
  firstname TEXT,
  hashedpassword TEXT,
  lastname TEXT,
  location TEXT,
  notificationtypesettings TEXT,
  phonenumber TEXT
);

--
-- Name: property_pkey; Type: CONSTRAINT; Schema: public; Tablespace: 
--

ALTER TABLE ONLY property
  ADD CONSTRAINT property_pkey PRIMARY KEY (id);

--
-- Name: user_pkey; Type: CONSTRAINT; Schema: public; Tablespace: 
--

ALTER TABLE ONLY "user"
  ADD CONSTRAINT user_pkey PRIMARY KEY (id);

--
-- PostgreSQL database dump complete
--

