create database Userprofile;
use Userprofile;
show tables;
create table users(id integer primary key, username varchar(255), password varchar(255));
select * from userprofile.users;
insert into users values(1, "navin", "n@123"),(2, "sujit", "s@123");
update userprofile.users set password = "$2y$10$RPXR3ALq.P9kLOHaviR4ce2Cs9gswWbNpGNLYCNsiF6qCOTNvubwi" where id = 2;

use foodies;
create table users(id integer primary key, username varchar(255), password varchar(255));
select * from foodies.users;
create database Books;
use Books;
show tables;
select * from book_details;
create database bankapp;
use bankapp;
show tables;
select * from sign_up_table;
truncate table transactions_history;
select * from transactions_history;
delete from transactions_history where id = 12;
drop table transactions_history;
INSERT INTO transactions_history (account_number, transaction_type, amount, status, timestamp, recipient_account) 
VALUES 
('123456789', 'deposit', 1500.00, 'completed', '2024-03-11 10:00:00', '9876543210'),
('123456789', 'withdrawal', 200.00, 'completed', '2024-03-11 12:30:00', '9876543210'),
('123456789', 'transfer', -300.00, 'completed', '2024-03-11 15:45:00', '1234567890');

create database hospitalApp;
use hospitalApp;
show tables;
select * from patients_table;
select * from doctor_table;
select * from appointments_table;

truncate table patients_table;

create database securitydemo;
use securitydemo;
create table users(username varchar(50) NOT null, PASSWORD varchar(120) not null, ENABLED tinyint(1) not null, primary key (username));
show tables;
select * from customer;
create table authorities (username varchar(50) NOT null, authority varchar(50) not null, key username (username), constraint authorities_ibfk_1 foreign key(username) references users(username));

insert into users values('shaik', '$2a$12$7ki9U4Nssf7sCBb8X/Kumuw1vK8KGOz3iWYbWKwfxtChEvBngylwS' , 1);

insert into authorities values('SHAIK', 'ROLE_USER');

create database auth_db;
use auth_db;
create database doctor_db;
create database patient_db;
create database appointment_db;