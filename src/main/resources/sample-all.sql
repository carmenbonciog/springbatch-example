DROP TABLE BookingExtract IF EXISTS;

CREATE TABLE BookingExtract (

	id int(11)  AUTO_INCREMENT,
	street varchar(150),
	city varchar(100),
	zip int(10),
	state varchar(10),
	beds int(10),
	baths int(10),
	sqft int(10),
	accomodationType varchar(10),
	holidayDate date,
	price dec(10,2),
	latitude dec(9,6),
	longitude dec(9,6),
	PRIMARY KEY (id)

);

CREATE TABLE `gfmpoc-batch`.BookingExtract (
  id int(11) NOT NULL AUTO_INCREMENT,
  street varchar(150) DEFAULT NULL,
  city varchar(100) DEFAULT NULL,
  zip varchar(150) DEFAULT NULL,
  state varchar(10) DEFAULT NULL,
  beds varchar(150) DEFAULT NULL,
  baths varchar(150) DEFAULT NULL,
  sqft varchar(150) DEFAULT NULL,
  accomodationType varchar(150) DEFAULT NULL,
  holidayDate varchar(150) DEFAULT NULL,
  price varchar(150) DEFAULT NULL,
  latitude varchar(150) DEFAULT NULL,
  longitude varchar(150) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 24
AVG_ROW_LENGTH = 1489
CHARACTER SET utf8
COLLATE utf8_general_ci;