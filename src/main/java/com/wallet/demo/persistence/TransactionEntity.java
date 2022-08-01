package com.wallet.demo.persistence;

import com.wallet.demo.models.enums.TransactionType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This entity represents the Transaction table.
 *
 * @author David Winn
 */
@Entity
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "account_id")
    private int accountId;

    private double amount;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    public TransactionEntity() {
        // Default constructor for hibernate.
    }

    public TransactionEntity(String transactionId, int accountId, double amount, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public String getId() {
        return transactionId;
    }

    public void setId(String id) {
        this.transactionId = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
