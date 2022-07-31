package com.wallet.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * The response for a transaction request.
 *
 * @author David Winn
 */
public class TransactionResponse {

    @JsonProperty("transaction_id")
    private UUID transactionId;

    private boolean success;

    private double balance;

    public TransactionResponse(UUID transactionId, boolean success, double balance) {
        this.transactionId = transactionId;
        this.success = success;
        this.balance = balance;
    }
}
