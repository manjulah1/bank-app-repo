package com.cognologix.bankapplication.exceptions;

import com.cognologix.bankapplication.constants.ExceptionMessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NullArgumentException.class)
    public ResponseEntity<ErrorResponse> handleNullArgumentException(final NullArgumentException nae) {
        ErrorResponse errorResponse = new ErrorResponse(ExceptionMessageConstants.NULL_ARGUMENT.getErrorCode(), nae.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientInitialBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientInitialBalanceException(final InsufficientInitialBalanceException iisbe) {
        ErrorResponse errorResponse = new ErrorResponse(ExceptionMessageConstants.INSUFFCIENT_INITIAL_BALANCE.getErrorCode(), iisbe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value() ,ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException rnfe) {
        ErrorResponse errorResponse = new ErrorResponse(ExceptionMessageConstants.RESOURCE_NOT_FOUND.getErrorCode(), rnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(ZeroAmountException.class)
    public ResponseEntity<ErrorResponse> handleZeroDepositionException(final ZeroAmountException zde) {
        ErrorResponse errorResponse = new ErrorResponse(ExceptionMessageConstants.ZERO_AMOUNT.getErrorCode(), zde.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(AccountDeletionException.class)
    public ResponseEntity<ErrorResponse> handleAccountDeletionException(final AccountDeletionException ade) {
        ErrorResponse errorResponse = new ErrorResponse(ExceptionMessageConstants.ACCOUNT_DELETION.getErrorCode(), ade.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
