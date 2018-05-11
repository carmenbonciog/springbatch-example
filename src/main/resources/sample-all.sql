DROP TABLE BookingExtract IF EXISTS;

CREATE TABLE BookingExtract (

	id int(11) DEFAULT AUTO_INCREMENT,
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