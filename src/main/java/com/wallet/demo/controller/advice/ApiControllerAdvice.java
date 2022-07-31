package com.wallet.demo.controller.advice;

import com.wallet.demo.dto.ApiError;
import com.wallet.demo.exception.AccountNotFoundException;
import com.wallet.demo.exception.DuplicateAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller Advice to handle the exception
 *
 * @author David Winn
 */
@RestControllerAdvice
public class ApiControllerAdvice {

	@ExceptionHandler(AccountNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiError handleNotFoundException(AccountNotFoundException accountNotFoundException) {
		return new ApiError(HttpStatus.NOT_FOUND.value(), accountNotFoundException.getMessage());
	}

	@ExceptionHandler(DuplicateAccountException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiError handleDuplicateException(DuplicateAccountException duplicatePlayerException) {
		return new ApiError(HttpStatus.CONFLICT.value(), duplicatePlayerException.getMessage());
	}

}
