package com.wallet.demo.persistence;

import com.wallet.demo.models.enums.TransactionType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * This entity represents the Transaction table.
 *
 * @author David Winn
 */
@Entity
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    private UUID id;

    @Column(name = "account_id")
    private int accountId;

    private double amount;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    public TransactionEntity() {
        // Default constructor for hibernate.
    }

    public TransactionEntity(UUID id, int accountId, double amount, TransactionType transactionType) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
