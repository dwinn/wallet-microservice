package com.wallet.demo.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallet.demo.models.enums.TransactionType;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * DTO describing a single transaction.
 *
 * @author David Winn
 */
public class Transaction {

    @NotEmpty
    @JsonProperty("transaction_id")
    private UUID transactionId;

    @NotEmpty
    @JsonProperty("account_id")
    private int accountId;

    @NotEmpty
    @JsonProperty("amount")
    private double amount;

    @NotEmpty
    @JsonProperty("transaction_type")
    private TransactionType transactionType;

    // @JsonCreator is a newer method of doing the no arg constructor for deserialization.
    @JsonCreator
    public Transaction(UUID transactionId, int accountId, double amount, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
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
