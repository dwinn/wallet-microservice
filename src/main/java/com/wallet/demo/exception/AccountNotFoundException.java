package com.wallet.demo.exception;

/**
 * Exception to throw when account not found.
 *
 * @author David Winn
 */
public class AccountNotFoundException extends RuntimeException {

	public AccountNotFoundException(String message) {
		super(message);
	}
}
