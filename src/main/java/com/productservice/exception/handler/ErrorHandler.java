package com.productservice.exception.handler;

import com.opencsv.exceptions.CsvRuntimeException;
import com.productservice.exception.ProductDataException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

  private static final StringWriter STRING_WRITER = new StringWriter();

  @ExceptionHandler(ProductDataException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> productDataException(ProductDataException exception) {
    logErrorTrace(exception);
    return buildErrorResponseEntity(exception.getMessage());
  }

  @ExceptionHandler(CsvRuntimeException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> csvRuntimeException(CsvRuntimeException exception) {
    logErrorTrace(exception);
    return buildErrorResponseEntity("Error while reading product data");
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> unknownException(Exception exception) {
    logErrorTrace(exception);
    return buildErrorResponseEntity("Internal Server Error");
  }

  private ResponseEntity<ErrorResponse> buildErrorResponseEntity(String message) {
    return new ResponseEntity<>(
        ErrorResponse.builder()
            .message(message)
            .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .timestamp(LocalDateTime.now())
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private void logErrorTrace(Exception exception) {
    exception.printStackTrace(new PrintWriter(STRING_WRITER));
    log.error(STRING_WRITER.toString());
  }
}
