package com.kantor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception ) {
        return new ResponseEntity<>("User with given id doesn't exist.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleTitleAlreadyExistException(UserAlreadyExistException exception ) {
        return new ResponseEntity<>("User already exist.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountBalanceNotFoundException.class)
    public ResponseEntity<Object> handleAccountBalanceNotFoundException(AccountBalanceNotFoundException exception) {
        return new ResponseEntity<>("Account doesn't exist.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountWithdrawalException.class)
    public ResponseEntity<Object> handleAccountWithdrawalException(AccountWithdrawalException exception) {
        return new ResponseEntity<>("You don't have enough money.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongValidationException.class)
    public ResponseEntity<Object> handleWrongValidationException(WrongValidationException exception) {
        return new ResponseEntity<>("Wrong account validation",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<Object> handleCurrencyNotFoundException(CurrencyNotFoundException exception) {
        return new ResponseEntity<>("Wrong currency",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException exception) {
        return new ResponseEntity<>("Order with given id doesn't exist",HttpStatus.BAD_REQUEST);
    }
}
