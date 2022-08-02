package com.wallet.demo.controller.advice;

import com.wallet.demo.exception.*;
import com.wallet.demo.models.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller Advice to handle exceptions thrown during API calls.
 * Allows exceptions to be easily managed.
 *
 * @author David Winn
 */
@RestControllerAdvice
public class ApiControllerAdvice {

	@ExceptionHandler(AccountNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiError handleAccountNotFoundException(AccountNotFoundException exception) {
		return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}

	@ExceptionHandler(DuplicateAccountException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiError handleDuplicateAccountException(DuplicateAccountException exception) {
		return new ApiError(HttpStatus.CONFLICT.value(), exception.getMessage());
	}

	@ExceptionHandler(TransactionExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiError handleTransactionExistsException(TransactionExistsException exception) {
		return new ApiError(HttpStatus.CONFLICT.value(), exception.getMessage());
	}

	@ExceptionHandler(InvalidBalanceException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ApiError handleInvalidBalanceException(InvalidBalanceException exception) {
		return new ApiError(HttpStatus.CONFLICT.value(), exception.getMessage());
	}

	@ExceptionHandler(InvalidTransactionTypeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleInvalidTransactionTypeException(InvalidTransactionTypeException exception) {
		return new ApiError(HttpStatus.CONFLICT.value(), exception.getMessage());
	}
}
