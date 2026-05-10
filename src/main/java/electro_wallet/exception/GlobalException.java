package electro_wallet.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorStatus> ApiExceptionHandler(ApiException ex, HttpServletRequest request) {
        ErrorStatus error = new ErrorStatus(
                ex.getStatus().value(),
                ex.getStatus().getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorStatus> ExceptionHandler(Exception ex, HttpServletRequest request) {
        ErrorStatus error = new ErrorStatus(
                500,
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(500).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorStatus> MethodArgumentNotValidExceptionHandler
            (MethodArgumentNotValidException ex, HttpServletRequest request) {

        String ErrorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorStatus error = new ErrorStatus(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                ErrorMessage,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorStatus> handleConflict(HttpServletRequest request) {
        ErrorStatus error = new ErrorStatus(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                "Ошибка целостности данных (возможно, такой Email или номер уже занят)",
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
