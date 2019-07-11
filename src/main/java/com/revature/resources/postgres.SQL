create or replace table Users(
    id serial primary key,
    User varchar not null,
    Pass varchar not null,
    FirstName varchar not null,
    LastName varchar not null
    AcctType integer 
);

create or replace table Accounts(
    id serial primary key,
    user integer references Users(id),
    Balance float,
    AccountType varchar
);