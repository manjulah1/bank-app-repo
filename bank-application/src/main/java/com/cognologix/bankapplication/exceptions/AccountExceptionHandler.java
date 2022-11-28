package com.cognologix.bankapplication.exceptions;

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
public class AccountExceptionHandler {

    @ExceptionHandler(NullArgumentException.class)
    public ResponseEntity<Object> handleNullArgumentException(final NullArgumentException nae) {
        return new ResponseEntity<>(nae.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientInitialBalanceException.class)
    public ResponseEntity<Object> handleInsufficientInitialBalanceException(final InsufficientInitialBalanceException iisbe) {
        return new ResponseEntity<>(iisbe.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(final Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleResourceNotFoundException(final ResourceNotFoundException rnfe) {
        return new ResponseEntity<>(rnfe.getMessage(), HttpStatus.NOT_FOUND);
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

    @ExceptionHandler(ZeroDepositionException.class)
    public ResponseEntity<Object> handleZeroDepositionException(final ZeroDepositionException zde) {
        return new ResponseEntity<>(zde.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountDeletionException.class)
    public ResponseEntity<Object> handleAccountDeletionException(final AccountDeletionException ade) {
        return new ResponseEntity<>(ade.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
