DROP DATABASE Maskaliova_contacts;
CREATE DATABASE Maskaliova_contacts;

USE Maskaliova_contacts;
CREATE TABLE contact (
  id        INT UNSIGNED   NOT NULL    AUTO_INCREMENT    PRIMARY KEY,
  f_name            VARCHAR(30)    NOT NULL,
  l_name            VARCHAR(30)    NOT NULL,
  p_name            VARCHAR(30)    NOT NULL,
  b_date            DATE           NOT NULL,
  sex               ENUM('male', 'female') NOT NULL,
  nationality       VARCHAR(30),
  marital_status    ENUM('married','single'),
  web_site          VARCHAR(50),
  email             VARCHAR(50)   NOT NULL ,
  company           VARCHAR(100),
  country           VARCHAR(100),
  city              VARCHAR(50),
  street            VARCHAR(50),
  house_n           VARCHAR(10),
  post_index        INT UNSIGNED,
  avatar            VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE phone_number(
  id                INT UNSIGNED    NOT NULL    AUTO_INCREMENT      PRIMARY KEY,
  owner_id          INT UNSIGNED    NOT NULL,
  country_code      VARCHAR(5)      NOT NULL,
  operator_code     VARCHAR(5)      NOT NULL,
  number            VARCHAR(10)     NOT NULL,
  type              ENUM('home','mobile')   NOT NULL,
  comment           TINYTEXT,
  CONSTRAINT contact_fk
  FOREIGN KEY (owner_id)
  REFERENCES contact(id)
                         ON DELETE CASCADE
)ENGINE=InnoDB;


CREATE TABLE attachment(
  id        INT UNSIGNED     NOT NULL    AUTO_INCREMENT    PRIMARY KEY,
  owner_id         INT UNSIGNED     NOT NULL,
  name             VARCHAR(100)     NOT NULL,
  path             VARCHAR(200)     NOT NULL,
  download_time    DATE             NOT NULL,
  comment          VARCHAR(200),
  FOREIGN KEY (owner_id) REFERENCES contact(id)
                       ON DELETE  CASCADE
)ENGINE=InnoDB;
