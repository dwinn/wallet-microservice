package com.wallet.demo.persistence;

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
    private int id;

    private String name;

    private double balance;

    public AccountEntity() {
        // Default constructor for hibernate.
    }

    public AccountEntity(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
