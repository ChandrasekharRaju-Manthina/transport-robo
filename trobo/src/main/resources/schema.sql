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
commit;