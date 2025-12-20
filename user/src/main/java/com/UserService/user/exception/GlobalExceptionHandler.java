package com.UserService.user.exception;

import com.UserService.user.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    //Triggered when @Valid fails in DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().
                stream() .collect(Collectors.
                        toMap(fe ->
                                fe.getField(), fe -> fe.getDefaultMessage(),
                                (a, b) -> a ));
        ApiError error=ApiError.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation Failed")
                .path(request.getRequestURI())
                .code("VALIDATION_ERROR")
                .fieldErrors(fieldErrors)
                .build();
        return ResponseEntity.badRequest().body(error);
    }

    //Triggered when throw new DuplicateResourceException like ("Username already exists")
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicate(DuplicateResourceException ex, HttpServletRequest request) {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now()) .
                status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .code("DUPLICATE_RESOURCE")
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    //Triggered when  throw new NotFoundException like ("Username not found")
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .code("NOT_FOUND")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    //Triggered when the keycloak registration service failed
    @ExceptionHandler(KeyCloakCreationException.class)
    public ResponseEntity<ApiError> keycloakFailed(KeyCloakCreationException ex,
                                                   HttpServletRequest request){
        ApiError error=ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .code("Keycloak_Error")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    //Triggered when the database rejects an insert/update (e.g., duplicate email because of unique constraint)

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex,
                                                        HttpServletRequest request){
        ApiError error= ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message("Database constraint violation")
                .path(request.getRequestURI())
                .code("DB_CONSTRAINT_VIOLATION")
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    //Triggered when something goes wrong during a DB transaction (e.g., invalid data type, failed commit)
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiError> handleTransaction(TransactionSystemException ex,
                                                      HttpServletRequest request) {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Transaction failed") .path(request.getRequestURI())
                .code("TRANSACTION_ERROR")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    //Catches any other error not handled above
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex,
                                                  HttpServletRequest request) {
         ApiError error = ApiError.builder()
                 .timestamp(LocalDateTime.now())
                 .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                 .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                 .message("Unexpected server error")
                 .path(request.getRequestURI())
                 .code("INTERNAL_ERROR")
                 .build();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
