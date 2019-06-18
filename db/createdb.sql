CREATE DATABASE Maskaliova_contacts;

USE Maskaliova_contacts;
CREATE TABLE contact (
  contact_id        INT UNSIGNED   NOT NULL    AUTO_INCREMENT    PRIMARY KEY,
  f_name            VARCHAR(30)    NOT NULL,
  l_name            VARCHAR(30)    NOT NULL,
  p_name            VARCHAR(30)    NOT NULL,
  b_date            DATETIME       NOT NULL,
  sex               ENUM('M', 'F') NOT NULL,
  nationality       VARCHAR(30),
  marital_status    ENUM('married','widowed','single','divorced'),
  web_site          VARCHAR(50),
  email             VARCHAR(50)   NOT NULL ,
  company           VARCHAR(100),
  country           VARCHAR(100),
  city              VARCHAR(50),
  street            VARCHAR(50),
  house_n           INT UNSIGNED,
  post_index        INT UNSIGNED

) ENGINE=InnoDB DEFAULT CHARSET = utf8;
ALTER TABLE contact MODIFY house_n VARCHAR(10);

CREATE TABLE phone_number(
  number_id         INT UNSIGNED    NOT NULL    AUTO_INCREMENT      PRIMARY KEY,
  owner_id          INT UNSIGNED    NOT NULL,
  country_code      VARCHAR(5)      NOT NULL,
  operator_code     VARCHAR(5)      NOT NULL,
  number            VARCHAR(10)     NOT NULL,
  type              ENUM('h','m')   NOT NULL,
  comment           TINYTEXT,
  CONSTRAINT contact_fk
  FOREIGN KEY (owner_id)
  REFERENCES contact(contact_id)
)ENGINE=InnoDB;

CREATE TABLE attachment(
  attach_id        INT UNSIGNED     NOT NULL    AUTO_INCREMENT    PRIMARY KEY,
  owner_id         INT UNSIGNED     NOT NULL,
  path             VARCHAR(200)     NOT NULL,
  FOREIGN KEY (owner_id) REFERENCES contact(contact_id)
)ENGINE=InnoDB;
ALTER TABLE attachment ADD COLUMN comment VARCHAR(200);
ALTER TABLE attachment ADD COLUMN name VARCHAR(100) NOT NULL;
ALTER TABLE attachment ADD COLUMN download_time DATE NOT NULL;