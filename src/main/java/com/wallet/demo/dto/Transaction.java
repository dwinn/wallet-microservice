package com.wallet.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * DTO describing a single transaction.
 *
 * @author David Winn
 */
public class Transaction {

    @JsonProperty("transaction_id")
    private UUID transactionId;

    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty("funds")
    private double funds;

    @JsonProperty("transaction_type")
    private String transactionType;

    // @JsonCreator is a newer method of doing the no arg constructor for deserialization.
    @JsonCreator
    public Transaction(UUID transactionId, int accountId, double funds, String transactionType) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.funds = funds;
        this.transactionType = transactionType;
    }
}
