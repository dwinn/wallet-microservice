package com.wallet.demo.exception;

/**
 * Exception to throw when an account with the same name is already present.
 *
 * @author David Winn
 */
public class DuplicateAccountException extends RuntimeException {

	public DuplicateAccountException(String message) {
		super(message);
	}
}
