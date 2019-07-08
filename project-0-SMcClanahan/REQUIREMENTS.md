# Project 0 Requirements
A Mavenized Java 8 CLI program connected to a PostgreSQL database. Submit a README.md with a proposal that matches most requirements below. You may use the Bank App proposal example for reference, or as your project itself.

## Requirements
### Tools & APIs
- [x] Java SE 8
- [x] Maven 3
- [] PostgreSQL 9+
- [] `java.io` Scanner, BufferedReader/Buffered
- [] `java.util` Collections API
- [] `java.sql` JDBC - PreparedStatement, CallableStatement
- [] JUnit 4
- [] log4j (or other logging framework)

### Architecture
- [] Multiple Packages
- [] Interfaces/Abstract Classes
- [] OOP - Abstraction, Encapsulation, Inheritance, Polymorphism
- [] Singleton
- [] Factory
- [] SQL Normalization
- [] PL/pgSQL

### Functionality
- [] CRUD - Create, Read, Update, Delete
- [] CLI - command-line args and/or event-driven text menus
- [] Login - Authentication & Authorization
- [] Persisting State - Save/Load to file or DB

### Presentation
- [] Prepare a demonstration of functionality requirements, no more than 5-10 minutes
- [] Present using standalone app through terminal execution

## Example: The Banking App
- [x] Build the application using Java 8.
- [] All interaction with the user should be done through the console using the `Scanner` class.
- [] Customers of the bank should be able to register with a username and password, and apply to open an account.
- [] Customers should be able to apply for joint accounts.
- [] Once the account is open, customers should be able to withdraw, deposit, and transfer funds between accounts.
- [] All basic validation should be done, such as trying to input negative amounts, overdrawing from accounts etc.
- [] Employees of the bank should be able to view all of their customers information. This includes:
    - [] Account information
    - [] Account balances
    - [] Personal information
- [] Employees should be able to approve/deny open applications for accounts.
- [] Bank admins should be able to view and edit all accounts. This includes:
    - [] Approving/denying accounts
    - [] Withdrawing, depositing, transferring from all accounts
    - [] Canceling accounts
- [] All information should be persisted using text files and serialization.
- [] 100% test coverage is expected using JUnit. You should be using TDD.
- [] Logging should be accomplished using Log4J. All transactions should be logged.
- [] Create an SQL script that will create a user in an SQL database and a table schema for storing your bank users and account information.
- [] Your database should include at least 1 stored procedure.
- [] Have your bank application connect to your SQL database using JDBC and store all information that way.
- [] You should use the DAO design pattern for data connectivity.