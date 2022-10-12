package mil.af.welcometoarmy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<Object> handleBindException(BindException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Error error = new Error(
                e.getAllErrors().get(0).getDefaultMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Error error = new Error(
                ExceptionMessage.UPLOAD_SIZE_EXCEED,
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException (EntityNotFoundException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Error error = new Error(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException (IllegalArgumentException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Error error = new Error(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = {TransactionSystemException.class})
    public ResponseEntity<Object> handleTransactionSystemException (TransactionSystemException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "";
        if (e.getRootCause() != null) message = e.getRootCause().getMessage();

        Error error = new Error(
                message,
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException (MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Error error = new Error(
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException (HttpServletRequest request, AccessDeniedException e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        String exceptionMessage = (String) request.getAttribute("exception");
        if (exceptionMessage == null) exceptionMessage = "접근 권한이 없습니다.";

        Error error = new Error(
                exceptionMessage,
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException (Exception e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        Error error = new Error(
                e.getCause().getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(error, httpStatus);
    }
}
