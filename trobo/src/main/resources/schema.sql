create table Spittle (
	id identity,
	message varchar(140) not null,
	created_at timestamp not null,
	latitude double,
	longitude double
);

create table Spitter (
	id identity,
	username varchar(20) unique not null,
	password varchar(20) not null,
	first_name varchar(30) not null,
	last_name varchar(30) not null,
	email varchar(30) not null
);

create table Address (
	id identity,
	address_line varchar(100) unique not null,
	city varchar(30) not null,
	state varchar(30) not null,
	zip varchar(30) not null,
	country varchar(30) not null
);

create table Driver (
	id identity,
	name varchar(100) unique not null,
	licenseNumber varchar(30) not null,
	phoneNumber varchar(30) not null,
	yearsOfExperience varchar(30) not null
);

create table Vehicle (
	id identity,
	vehicleNumber varchar(10) unique not null,
	seats varchar(30) not null,
	trackingDeviceLink varchar(30) not null,
	driverId varchar(30) not null,
	priceUnit varchar(10)
);

insert into Address (address_line, city, state, zip, country) values ('Innovative multiplex', 'Bangalore', 'Karnataka', '560037', 'Ïndia');
insert into Address (address_line, city, state, zip, country) values ('Tin Factory', 'Bangalore', 'Karnataka', '560037', 'Ïndia');
insert into Address (address_line, city, state, zip, country) values ('Majestic', 'Bangalore', 'Karnataka', '560037', 'Ïndia');
insert into Address (address_line, city, state, zip, country) values ('Silk board', 'Bangalore', 'Karnataka', '560037', 'Ïndia');
insert into Address (address_line, city, state, zip, country) values ('Bhanerugatta', 'Bangalore', 'Karnataka', '560037', 'Ïndia');
insert into Address (address_line, city, state, zip, country) values ('Bellandur', 'Bangalore', 'Karnataka', '560037', 'Ïndia');
insert into Address (address_line, city, state, zip, country) values ('ITPL', 'Bangalore', 'Karnataka', '560037', 'Ïndia');
insert into Address (address_line, city, state, zip, country) values ('Dommuluru', 'Bangalore', 'Karnataka', '560037', 'Ïndia');
insert into Address (address_line, city, state, zip, country) values ('Yeswanthpur', 'Bangalore', 'Karnataka', '560037', 'Ïndia');
insert into Address (address_line, city, state, zip, country) values ('Shivaji Nagar', 'Bangalore', 'Karnataka', '560037', 'Ïndia');
insert into Address (address_line, city, state, zip, country) values ('Richmond road', 'Bangalore', 'Karnataka', '560037', 'Ïndia');

insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Chandu', 'ABCDEFGHIJKL', '1234567890', '5');
insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Anil', 'ABCDEFGHIJKL', '1234567890', '5');
insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Raj', 'ABCDEFGHIJKL', '1234567890', '5');
insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Prabhu', 'ABCDEFGHIJKL', '1234567890', '5');
insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Lakshmi', 'ABCDEFGHIJKL', '1234567890', '5');
insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Sameer', 'ABCDEFGHIJKL', '1234567890', '5');
insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Venkat', 'ABCDEFGHIJKL', '1234567890', '5');
insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Shyam', 'ABCDEFGHIJKL', '1234567890', '5');
insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Chalapati', 'ABCDEFGHIJKL', '1234567890', '5');
insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Yashodara', 'ABCDEFGHIJKL', '1234567890', '5');
insert into Driver (name, licenseNumber, phoneNumber, yearsOfExperience) values ('Ankamu raju', 'ABCDEFGHIJKL', '1234567890', '5');

insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1234', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1232', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1231', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1230', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1233', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1235', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1236', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1237', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1238', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1239', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1255', '6', 'TEST LINK', '5');


commit;