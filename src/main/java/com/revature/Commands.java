package com.revature;

interface Commands {
    double Balance(String user, String acctType);
    double Deposit(String user, String acctType, float amount);
    double Withdrawl(String user, String acctType, float amount);
    void Close(String user, String acctType);

}