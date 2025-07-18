package com.liamfer.CloudCart;

import com.liamfer.CloudCart.dto.APICheckoutErrorMessage;
import com.liamfer.CloudCart.dto.APIMessage;
import com.liamfer.CloudCart.exceptions.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIMessage<Map<String, String>>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new APIMessage<Map<String, String>>(HttpStatus.BAD_REQUEST.value(),errors));
    }

    @ExceptionHandler({AuthenticationException.class, AuthenticationServiceException.class})
    public ResponseEntity<APIMessage<String>> AuthenticationExceptionHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new APIMessage<String>(HttpStatus.BAD_REQUEST.value(),"Invalid Credentials"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIMessage<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        APIMessage<String> message = new APIMessage<>(
                HttpStatus.BAD_REQUEST.value(),
                "Request body is missing or malformed"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler({ProductUnavailableException.class, ProductNotEnoughInStockException.class,
            ProductInOrderException.class})
    public ResponseEntity<APIMessage<String>> handleProductExceptions(Exception ex) {
        APIMessage<String> message = new APIMessage<>(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler({EmptyCartException.class})
    public ResponseEntity<APIMessage<String>> handleCartExceptions(Exception ex) {
        APIMessage<String> message = new APIMessage<>(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler({PaymentAlreadyCanceledException.class})
    public ResponseEntity<APIMessage<String>> handlePaymentAlreadyCanceledException(PaymentAlreadyCanceledException ex) {
        APIMessage<String> message = new APIMessage<>(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(StockNotEnoughException.class)
    public ResponseEntity<APICheckoutErrorMessage> handleStockNotEnoughException(StockNotEnoughException ex) {
        APICheckoutErrorMessage message = new APICheckoutErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                ex.getProductIds()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<APIMessage<String>> EntityExistsExceptionHandler(EntityExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new APIMessage<String>(HttpStatus.CONFLICT.value(),ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIMessage<String>> EntityNotFoundExceptionHandler(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new APIMessage<String>(HttpStatus.NOT_FOUND.value(),ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIMessage<String>> defaultExceptionHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIMessage<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage()));
    }
}
