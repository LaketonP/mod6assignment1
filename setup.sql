-- setup.sql

DROP TABLE IF EXISTS Staff;

CREATE TABLE Staff (
  id CHAR(9) NOT NULL,
  lastName VARCHAR(15),
  firstName VARCHAR(15),
  mi CHAR(1),
  address VARCHAR(20),
  city VARCHAR(20),
  state CHAR(2),
  telephone CHAR(10),
  email VARCHAR(40),
  PRIMARY KEY (id)
);
