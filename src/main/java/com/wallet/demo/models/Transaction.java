package com.wallet.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * DTO describing a single transaction.
 *
 * @author David Winn
 */
public class Transaction {

    @JsonProperty("transaction_id")
    @ApiModelProperty(required = true, example = "0fa6b8ca-11e4-11ed-961d-0242ac121144")
    @NotNull
    private String transactionId;

    @ApiModelProperty(required = true, example = "2")
    @JsonProperty("account_id")
    @NotNull
    private int accountId;

    @ApiModelProperty(required = true, example = "50.30")
    @JsonProperty("amount")
    @NotNull
    private double amount;

    @ApiModelProperty(required = true, example = "CREDIT")
    @JsonProperty("transaction_type")
    @NotNull
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
