create table Address (
	id identity,
	address_line varchar(100) unique not null,
	city varchar(30) not null,
	state varchar(30) not null,
	zip varchar(30) not null,
	country varchar(30) not null,
	longitude NUMERIC, 
	latitude NUMERIC,
	longitude double, 
	latitude double,
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

--add sex column
create table Employee (
	id identity,
	name varchar(50) not null,
	mangerId NUMERIC not null, 
	addressId NUMERIC null,
	status varchar(1) not null,
	sex varchar(1) not null
);

insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 1', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 2', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 3', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 4', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 5', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 6', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 7', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 8', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 9', 1, 1, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 9-1', 1, 1, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 10', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 11', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 12', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 13', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 14', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 15', 1, 2, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 16', 1, 2, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 17', 1, 3, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 18', 1, 3, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 19', 1, 3, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 20', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 21', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 22', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 23', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 24', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 25', 1, 4, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 26', 1, 5, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 27', 1, 5, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 28', 1, 5, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 29', 1, 5, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 30', 1, 5, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 31', 1, 5, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 32', 1, 5, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 33', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 34', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 35', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 36', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 37', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 38', 1, 6, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 39', 1, 6, 'A', 'M');

insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 40', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 41', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 42', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 43', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 44', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 45', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 46', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 47', 1, 7, 'A', 'M');
insert into Employee (name, mangerId, addressId, status, sex) values ('Siva 48', 1, 7, 'A', 'M');

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
insert into TransportRequest (startDate, endDate, employeeId, shiftId, requestType, status) values ('2015-12-10', '2015-12-15', '49', '1', 'T','A');


insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('Innovative multiplex', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.95073','77.70005','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('Goplan-Mall', 'Bangalore', 'Karnataka', '560037','India','12.91434','77.59964','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('CENTRAL-MALL-BELLANDUR', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.91650','77.59280','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('ISKCON-TEMPLE', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.90625','77.55505','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('Kempegowda-station', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.97766','77.57302','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('Mantri-mall', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.99070','77.57199','Pending');
insert into Address (address_line, city, state, zip, country, latitude, longitude, status) values ('BULSKAMP', 'Bangalore', 'Karnataka', '560037', 'Ïndia','12.92569','77.61801','Pending');
insert into Address (address_line, city, state, zip, country, status) values ('Dommuluru', 'Bangalore', 'Karnataka', '560037', 'Ïndia','Pending');
insert into Address (address_line, city, state, zip, country, status) values ('Yeswanthpur', 'Bangalore', 'Karnataka', '560037', 'Ïndia','Pending');
insert into Address (address_line, city, state, zip, country, status) values ('Shivaji Nagar', 'Bangalore', 'Karnataka', '560037', 'Ïndia','Pending');
insert into Address (address_line, city, state, zip, country, status) values ('Richmond road', 'Bangalore', 'Karnataka', '560037', 'Ïndia','Pending');

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
