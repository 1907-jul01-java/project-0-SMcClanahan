drop table if exists accounts, userlogins, applications, userlogins_accounts;

create table UserLogins(
    id serial primary key,
    Username varchar not null,
    Pass varchar,
    FirstName varchar,
    LastName varchar,
    AccessType integer default 0
);

create table Accounts(
    id serial primary key,
    Balance float default 0,
    AcctType varchar
);

create table userlogins_accounts(
    Userfk integer references userlogins(id),
    Acctfk integer references accounts(id)
);

create table Applications(
    id serial primary key,
    userfk integer references UserLogins(id),
    AcctType varchar not null,
    AccountID integer references accounts(id),
    AccountStatus varchar
);

insert into userlogins (username, pass, firstname, lastname) values ('sean', 'luft', 'Sean', 'McClanahan');
insert into userlogins (username, pass, firstname, lastname) values ('kyle', 'luft', 'Kyle', 'McClanahan');
insert into userlogins (username, pass, firstname, lastname) values ('papa', 'luft', 'Richard', 'Lefley');
insert into Accounts (accttype, balance) values ('checking', 300);
insert into Accounts (accttype, balance) values ('retirement', 30000);
insert into Accounts (accttype, balance) values ('savings', 300);
insert into userlogins (username, pass, accesstype) values ('luft', 'luft', 1);
insert into applications(id, accttype) values (1, 'savings');

create or replace procedure CreateAccounts (UserID in integer) 
language sql
as $$
insert into accounts (accttype) select accttype from applications where id = Userid;
insert into userlogins_accounts (userfk, acctfk) select UserID, Max(id) from accounts;
$$;