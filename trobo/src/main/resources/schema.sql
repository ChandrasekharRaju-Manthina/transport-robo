create table Address (
	id identity,
	address_line varchar(100) unique not null,
	city varchar(30) not null,
	state varchar(30) not null,
	zip varchar(30) not null,
	country varchar(30) not null,
	longitude NUMERIC, 
	latitude NUMERIC,
	status varchar(30)
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

create table Shift (
	id identity,
	startTime varchar(10) not null,
	endTime varchar(10) not null
);

create table TransportRequest (
	id identity,
	startDate date  not null,
	endDate date not null,
	employeeId varchar not null,
	shiftId varchar not null,
	requestType varchar(1) not null,
	status varchar(1) not null
);

create table Employee (
	id identity,
	name varchar(50) not null,
	mangerId NUMERIC not null, 
	addressId NUMERIC null,
	status varchar(1) not null,
	sex varchar(1) not null
);

create table TripRoute (
	routeId varchar(50) not null,
	tripDate date not null,
	tripType varchar(5) not null,
	shiftId NUMERIC not null,
	vehicleId NUMERIC not null,
	tripDistance NUMERIC,
	paid varchar(1)
);

create table TripRouteEmployee (
	id identity,
	routeId varchar(50) not null,
	empId NUMERIC not null,
	vehicleId NUMERIC not null,
	tripTime varchar(10) not null,
);

insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 1', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 2', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 3', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 4', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 5', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 6', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 7', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 8', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 9', 1, 1, 'A', 'F');
--insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 9-1', 1, 1, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 10', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 11', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 12', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 13', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 14', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 15', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 16', 1, 2, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 17', 1, 3, 'A', 'F');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 18', 1, 3, 'A', 'F');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 19', 1, 3, 'A', 'F');

insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 20', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 21', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 22', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 23', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 24', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 25', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 26', 1, 5, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 27', 1, 5, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 28', 1, 5, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 29', 1, 5, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 30', 1, 5, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 31', 1, 5, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 32', 1, 5, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 33', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 34', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 35', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 36', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 37', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 38', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 39', 1, 6, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 40', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 41', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 42', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 43', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 44', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 45', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 46', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee 47', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Employee David Johnson 48', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('ESCORT', 1, 12, 'A', 'M');

insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '1', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '2', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '3', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '4', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '5', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '6', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '7', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '8', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '9', '1', 'T','A');

insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '10', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '11', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '12', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '13', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '14', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '15', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '16', '1', 'T','A');

insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '17', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '18', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '19', '1', 'T','A');

insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '20', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '21', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '22', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '23', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '24', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '25', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '26', '1', 'T','A');

insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '27', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '28', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '29', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '30', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '31', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '32', '1', 'T','A');

insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '33', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '34', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '35', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '36', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '37', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '38', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '39', '1', 'T','A');

insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '40', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '41', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '42', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '43', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '44', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '45', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '46', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '47', '1', 'T','A');
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-16', '2015-12-30', '48', '1', 'T','A');
--insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-10', '2015-12-15', '49', '1', 'T','A');


insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('Innovative multiplex', 'Bangalore', 'Karnataka', '560037', 'Ïndia', '12.952236', '77.6974433','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('Gopalan Innovation Mall', 'Bangalore', 'Karnataka', '560037','India','12.9145344','77.5972563','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('CENTRAL-MALL-BELLANDUR', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.9261483','77.6730996','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('ISKCON-TEMPLE', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.99404','77.5488983','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('Kempegowda-station', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.9768605','77.5701179','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('Inorbit Mall', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.979128','77.7264047','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('Vidhana Soudha', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.981084','77.5864703','Pending');
insert into Address (address_line, city, state, zip, country, status) values ('Dommuluru', 'Bangalore', 'Karnataka', '560037', 'Ïndia','Pending');
insert into Address (address_line, city, state, zip, country, status) values ('Yeswanthpur', 'Bangalore', 'Karnataka', '560037', 'Ïndia','Pending');
insert into Address (address_line, city, state, zip, country, status) values ('Shivaji Nagar', 'Bangalore', 'Karnataka', '560037', 'Ïndia','Pending');
insert into Address (address_line, city, state, zip, country, status) values ('Richmond road', 'Bangalore', 'Karnataka', '560037', 'Ïndia','Pending');
insert into Address (address_line, city, state, zip, country, status) values ('Escort', 'Bangalore', 'Karnataka', '560037', 'Ïndia','Pending');
--insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('Manayata Tech Park', 'Bangalore', 'Karnataka', '560016', 'Ïndia','12.9531159','77.6969939','Pending');


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

insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1234', '9', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1232', '7', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1231', '13', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1230', '7', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1233', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1235', '9', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1236', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1237', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1238', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1239', '6', 'TEST LINK', '5');
insert into Vehicle (vehicleNumber, seats, trackingDeviceLink, driverId) values ('AP 1255', '6', 'TEST LINK', '5');

insert into Shift (startTime, endTime) values ('09:30', '18:00');
insert into Shift (startTime, endTime) values ('08:00', '16:30');
insert into Shift (startTime, endTime) values ('11:00', '20:30');
insert into Shift (startTime, endTime) values ('13:00', '21:30');
insert into Shift (startTime, endTime) values ('09:30', '18:00');
insert into Shift (startTime, endTime) values ('09:30', '18:00');
insert into Shift (startTime, endTime) values ('09:30', '18:00');
insert into Shift (startTime, endTime) values ('09:30', '18:00');
insert into Shift (startTime, endTime) values ('09:30', '18:00');
insert into Shift (startTime, endTime) values ('09:30', '18:00');
insert into Shift (startTime, endTime) values ('09:30', '18:00');
insert into Shift (startTime, endTime) values ('09:30', '18:00');

commit;
