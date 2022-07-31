package com.wallet.demo.models;

import java.util.List;

/**
 * DTO describing a list of transactions.
 *
 * @author David Winn
 */
public class TransactionList {

    private List<Transaction> transactions;

    public TransactionList(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
