package com.revature;

interface Commands {
    double Balance();
    double Deposit(int acctType, double amount);
    double Withdrawl(int acctType, double amount);
    int GetAccounts();
    void Close();

}