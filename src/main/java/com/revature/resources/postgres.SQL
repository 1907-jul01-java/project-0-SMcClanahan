drop table if exists accounts, userlogins, applications;

create table UserLogins(
    id serial primary key,
    Username varchar not null,
    Pass varchar,
    FirstName varchar,
    LastName varchar,
    AccessType integer
);

create table Accounts(
    id serial primary key,
    Userfk integer references UserLogins(id),
    Balance float,
    AcctType varchar
);

create table Applications(
    id integer references UserLogins(id),
    AcctType varchar
);

insert into userlogins (username, pass, firstname, lastname) values ('sean', 'luft', 'Sean', 'McClanahan');
insert into Accounts (accttype, balance, userfk) values ('checking', 300, (SELECT id FROM userlogins where username = 'sean'));
insert into Accounts (accttype, balance, userfk) values ('savings', 300, (SELECT id FROM userlogins where username = 'sean'));