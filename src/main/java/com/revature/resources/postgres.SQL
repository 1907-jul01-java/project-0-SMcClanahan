drop table if exists accounts, userlogins;

create table UserLogins(
    id serial primary key,
    Username varchar not null,
    Pass varchar not null,
    FirstName varchar not null,
    LastName varchar not null,
    AccessType integer
);

create table Accounts(
    id serial primary key,
    Userfk integer references UserLogins(id),
    Balance float,
    AcctType varchar
);