package com.wallet.demo.models;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public ApiError() {
		// Default constructor for hibernate.
	}
}
