# Project 0 Requirements
A Mavenized Java 8 CLI program connected to a PostgreSQL database. Submit a README.md with a proposal that matches most requirements below. You may use the Bank App proposal example for reference, or as your project itself.

## Requirements
### Tools & APIs
- [x] Java SE 8
- [x] Maven 3
- [x] PostgreSQL 9+
- [x] `java.io` Scanner, BufferedReader/Buffered
- [x] `java.util` Collections API
- [x] `java.sql` JDBC - PreparedStatement, CallableStatement
- [x] JUnit 4
- [] log4j (or other logging framework)

### Architecture
- [x] Multiple Packages
- [x] Interfaces/Abstract Classes
- [x] OOP - Abstraction, Encapsulation, Inheritance, Polymorphism
- [] Singleton
- [x] Factory
- [x] SQL Normalization
- [x] PL/pgSQL

### Functionality
- [x] CRUD - Create, Read, Update, Delete
- [x] CLI - command-line args and/or event-driven text menus
- [x] Login - Authentication & Authorization
- [x] Persisting State - Save/Load to file or DB

### Presentation
- [] Prepare a demonstration of functionality requirements, no more than 5-10 minutes
- [] Present using standalone app through terminal execution

## Example: The Banking App
- [x] Build the application using Java 8.
- [x] All interaction with the user should be done through the console using the `Scanner` class.
- [x] Customers of the bank should be able to register with a username and password, and apply to open an account.
- [] Customers should be able to apply for joint accounts.
- [x] Once the account is open, customers should be able to withdraw, deposit, and transfer funds between accounts.
- [x] All basic validation should be done, such as trying to input negative amounts, overdrawing from accounts etc.
- [x] Employees of the bank should be able to view all of their customers information. This includes:
    - [x] Account information
    - [x] Account balances
    - [x] Personal information
- [x] Employees should be able to approve/deny open applications for accounts.
- [] Bank admins should be able to view and edit all accounts. This includes:
    - [x] Approving/denying accounts
    - [x] Withdrawing, depositing, transferring from all accounts
    - [x] Canceling accounts
- [x] All information should be persisted using text files and serialization.
- [] 100% test coverage is expected using JUnit. You should be using TDD.
- [] Logging should be accomplished using Log4J. All transactions should be logged.
- [x] Create an SQL script that will create a user in an SQL database and a table schema for storing your bank users and account information.
- [] Your database should include at least 1 stored procedure.
- [x] Have your bank application connect to your SQL database using JDBC and store all information that way.
- [x] You should use the DAO design pattern for data connectivity.