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
    Userfk integer references UserLogins(id),
    Balance float,
    AcctType varchar
);

create table Applications(
    Userid integer references UserLogins(id),
    AcctType varchar,
    AccountID integer references accounts(id)
);

create table userlogins_accounts(
    Userfk integer references userlogins(id),
    Acctfk integer references accounts(id)
);

insert into userlogins (username, pass, firstname, lastname) values ('sean', 'luft', 'Sean', 'McClanahan');
insert into userlogins (username, pass, firstname, lastname) values ('kyle', 'luft', 'Kyle', 'McClanahan');
insert into userlogins (username, pass, firstname, lastname) values ('papa', 'luft', 'Richard', 'Lefley');
insert into Accounts (accttype, balance, userfk) values ('checking', 300, (SELECT id FROM userlogins where username = 'sean'));
insert into Accounts (accttype, balance, userfk) values ('retirement', 30000, (SELECT id FROM userlogins where username = 'kyle'));
insert into Accounts (accttype, balance, userfk) values ('savings', 300, (SELECT id FROM userlogins where username = 'sean'));
insert into userlogins (username, pass, accesstype) values ('luft', 'luft', 1);

create procedure CreateAccounts (UserID integer, accountsID integer) 
language sql
as $$
select accttype from applications where ID = UserID;
insert into accounts (accttype) values (applications.accttype);
accountID = select scope_identity();
insert into userlogins_accounts (userfk, acctfk) values (UserID, accountID);
$$;