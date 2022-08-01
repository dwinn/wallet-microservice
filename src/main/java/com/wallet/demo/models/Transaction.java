package com.wallet.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallet.demo.models.enums.TransactionType;

import javax.validation.constraints.NotNull;

/**
 * DTO describing a single transaction.
 *
 * @author David Winn
 */
public class Transaction {

    @NotNull
    @JsonProperty("transaction_id")
    private String transactionId;

    @NotNull
    @JsonProperty("account_id")
    private int accountId;

    @NotNull
    @JsonProperty("amount")
    private double amount;

    @NotNull
    @JsonProperty("transaction_type")
    private TransactionType transactionType;

    public Transaction(String transactionId, int accountId, double amount, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public Transaction() {
        // Default constructor for hibernate.
    }

    public String getTransactionId() {
        return transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
