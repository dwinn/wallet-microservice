package com.wallet.demo.exception;

/**
 * Exception to throw when a transaction is received with an unknown transaction type.
 *
 * @author David Winn
 */
public class InvalidTransactionTypeException extends RuntimeException {
    public InvalidTransactionTypeException(String message) {
        super(message);
    }
}
