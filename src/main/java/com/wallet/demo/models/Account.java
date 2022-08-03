package com.wallet.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallet.demo.persistence.AccountEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * A DTO describing an account.
 *
 * @author David Winn
 */
public class Account {

    @JsonProperty("id")
    @ApiModelProperty(required = true, example = "10")
    @NotNull
    private int id;

    @JsonProperty("name")
    @ApiModelProperty(required = true, example = "Best Player 4")
    @NotEmpty
    private String name;

    @JsonProperty("balance")
    @ApiModelProperty(required = true, example = "5.50")
    private double balance;

    public Account(AccountEntity account) {
        this.id = account.getId();
        this.name = account.getName();
        this.balance = account.getBalance();
    }

    public Account() {
        // Default constructor for hibernate.
    }

    public Account(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}