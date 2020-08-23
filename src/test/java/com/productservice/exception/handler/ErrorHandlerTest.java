package com.productservice.exception.handler;

import com.opencsv.exceptions.CsvRuntimeException;
import com.productservice.exception.ProductDataException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ErrorHandlerTest {

  private ErrorHandler errorHandler;

  @BeforeEach
  public void setup() {
    errorHandler = new ErrorHandler();
  }

  @Test
  public void shouldHandleProductDataException() {
    var errorResponseEntity =
        errorHandler.productDataException(
            new ProductDataException("Error while reading product data"));
    Assertions.assertThat(errorResponseEntity.getBody()).isNotNull();
    Assertions.assertThat(errorResponseEntity.getStatusCode())
        .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    Assertions.assertThat(errorResponseEntity.getBody().getMessage())
        .isEqualTo("Error while reading product data");
  }

  @Test
  public void shouldHandleCsvRuntimeException() {
    var errorResponseEntity =
        errorHandler.csvRuntimeException(new CsvRuntimeException("csv runtime exception"));
    Assertions.assertThat(errorResponseEntity.getBody()).isNotNull();
    Assertions.assertThat(errorResponseEntity.getStatusCode())
        .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    Assertions.assertThat(errorResponseEntity.getBody().getMessage())
        .isEqualTo("Error while reading product data");
  }

  @Test
  public void shouldHandleUnknownException() {
    var errorResponseEntity = errorHandler.unknownException(new RuntimeException("test"));
    Assertions.assertThat(errorResponseEntity.getBody()).isNotNull();
    Assertions.assertThat(errorResponseEntity.getStatusCode())
        .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    Assertions.assertThat(errorResponseEntity.getBody().getMessage())
        .isEqualTo("Internal Server Error");
  }
}
