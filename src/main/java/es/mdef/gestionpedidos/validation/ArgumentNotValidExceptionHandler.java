package es.mdef.gestionpedidos.validation;

import es.mdef.gestionpedidos.errors.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ArgumentNotValidExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex) {
        StringBuilder message = new StringBuilder();

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            exception.getBindingResult().getAllErrors().forEach(error -> {
                String errorMessage = error.getDefaultMessage();
                message.append(errorMessage).append("; ");
            });
        } else if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException exception = (ConstraintViolationException) ex;
            exception.getConstraintViolations().forEach(violation -> {
                String errorMessage = violation.getMessage();
                message.append(errorMessage).append("; ");
            });
        }
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Validation Error");
        errorResponse.setMessage(message.toString());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setError("Data Integrity Violation");
        errorResponse.setMessage("Valor duplicado en la base de datos");
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
