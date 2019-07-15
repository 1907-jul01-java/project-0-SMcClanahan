package com.revature;

interface Commands {
    double Balance();
    double Deposit(int acctType);
    double Withdrawl(int acctType);
    int GetAccounts();
    void Apply();
    void Transfer();
    void Close();

}