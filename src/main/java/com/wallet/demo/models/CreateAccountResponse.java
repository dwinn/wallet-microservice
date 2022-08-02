package com.wallet.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO representing a response to an API request to create an account.
 */
public class CreateAccountResponse {

    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty("success")
    private boolean success;

    public CreateAccountResponse(int accountId, boolean success) {
        this.accountId = accountId;
        this.success = success;
    }
}
