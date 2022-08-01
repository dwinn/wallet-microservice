package com.wallet.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String transactionType;

    public Transaction(String transactionId, int accountId, double amount, String transactionType) {
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
