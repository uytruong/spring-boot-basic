DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  email VARCHAR(30) NOT NULL,
  phone_number VARCHAR(20) NOT NULL,
  postal_address INT
);