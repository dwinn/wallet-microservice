package com.wallet.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The response for a transaction request.
 *
 * @author David Winn
 */
public class TransactionResponse {

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("balance")
    private double balance;

    public TransactionResponse(String transactionId, boolean success, double balance) {
        this.transactionId = transactionId;
        this.success = success;
        this.balance = balance;
    }

    public TransactionResponse() {
        // Default constructor for hibernate.
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
