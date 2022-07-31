package com.wallet.demo.exception;

/**
 * Exception to throw when a transaction already exists with a given ID.
 *
 * @author David Winn
 */
public class TransactionExistsException extends RuntimeException {
    public TransactionExistsException(String message) {
        super(message);
    }
}
