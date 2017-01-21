--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;

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
  id            BIGINT NOT NULL,
  propertykey   TEXT,
  propertyvalue TEXT
);

--
-- Name: user; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE "user" (
  id             BIGINT NOT NULL,
  username       TEXT,
  hashedpassword TEXT
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

