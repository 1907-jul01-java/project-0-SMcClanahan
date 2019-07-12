package com.revature;

interface Commands {
    double Balance(String acctType);
    double Deposit(String acctType, float amount);
    double Withdrawl(String acctType, float amount);
    void Close();

}