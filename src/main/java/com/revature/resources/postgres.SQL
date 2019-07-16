drop table if exists accounts, userlogins, applications;

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
    Balance float,
    AcctType varchar
);

create table userlogins_accounts(
    Userfk integer references userlogins(id),
    Acctfk integer references accounts(id)
);

create table Applications(
    id integer references UserLogins(id),
    AcctType varchar,
    AccountID integer references accounts(id)
);

insert into userlogins (username, pass, firstname, lastname) values ('sean', 'luft', 'Sean', 'McClanahan');
insert into userlogins (username, pass, firstname, lastname) values ('kyle', 'luft', 'Kyle', 'McClanahan');
insert into userlogins (username, pass, firstname, lastname) values ('papa', 'luft', 'Richard', 'Lefley');
insert into Accounts (accttype, balance) values ('checking', 300);
insert into Accounts (accttype, balance) values ('retirement', 30000);
insert into Accounts (accttype, balance) values ('savings', 300);
insert into userlogins (username, pass, accesstype) values ('luft', 'luft', 1);

create procedure CreateAccounts (UserID integer, accountsID integer) 
language sql
as $$
insert into accounts (accttype) select accttype from applications where id = Userid;
insert into userlogins_accounts (userfk, acctfk) select UserID, Max(id) from accounts);
$$;