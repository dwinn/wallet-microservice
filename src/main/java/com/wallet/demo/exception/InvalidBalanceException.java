package com.wallet.demo.exception;

/**
 * Exception thrown when a balance attempts to go below 0.
 *
 * @author David Winn
 */
public class InvalidBalanceException extends RuntimeException {
    public InvalidBalanceException(String message) {
        super(message);
    }
}