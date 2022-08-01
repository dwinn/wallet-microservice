package com.wallet.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallet.demo.models.enums.TransactionType;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * DTO describing a single transaction.
 *
 * @author David Winn
 */
public class Transaction {

    @NotNull
    @JsonProperty("transaction_id")
    private UUID transactionId;

    @NotNull
    @JsonProperty("account_id")
    private int accountId;

    @NotNull
    @JsonProperty("amount")
    private double amount;

    @NotNull
    @JsonProperty("transaction_type")
    private TransactionType transactionType;

    public Transaction(UUID transactionId, int accountId, double amount, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public Transaction() {
        // Default constructor for hibernate.
    }

    public UUID getTransactionId() {
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
