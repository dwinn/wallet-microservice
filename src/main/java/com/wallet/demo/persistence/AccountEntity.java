package com.wallet.demo.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This entity represents the Account table.
 *
 * @author David Winn
 */
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "balance")
    private double balance;

    public AccountEntity() {
        // Default constructor for hibernate.
    }

    public AccountEntity(int accountId, String accountName, double balance) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
