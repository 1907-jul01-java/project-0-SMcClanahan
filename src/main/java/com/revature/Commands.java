package com.revature;

interface Commands {
    int Balance(String user, String acctType);
    void Deposit(String user, String acctType, float amount);
    void Withdrawl(String user, String acctType, float amount);
    void Close(String user, String acctType);

}