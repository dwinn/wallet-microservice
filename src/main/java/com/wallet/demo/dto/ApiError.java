package com.wallet.demo.dto;

/**
 * DTO to store error details.
 *
 * @author David Winn
 */
public class ApiError {

	int status;

	String reason;

	public ApiError(int status, String reason) {
		this.status = status;
		this.reason = reason;
	}
}
