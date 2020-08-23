package com.productservice.service;

import com.productservice.exception.ProductDataException;
import com.productservice.repository.ProductRepository;
import com.productservice.util.TestingUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ProductServiceTest {

  @Mock private ProductRepository productRepository;
  private ProductService productService;

  @BeforeEach
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    productService = new ProductService(productRepository);
    Mockito.when(productRepository.getProducts()).thenReturn(TestingUtils.buildProducts(false));
  }

  @Test
  public void shouldReturnAllProducts() {
    var productDTOS = productService.getAllProducts();
    Assertions.assertThat(productDTOS).isNotEmpty();
    Assertions.assertThat(productDTOS).hasSize(2);

    TestingUtils.commonProductDTOAsserts(
        productDTOS.get(0), 1L, "LEGO #14362905", "100.00", "95.9", 1000L, "LEGO");
    TestingUtils.commonProductDTOAsserts(
        productDTOS.get(1), 10920L, "s.Oliver #744328", "23.99", "23.99", 10L, "s.Oliver");
  }

  @Test
  public void shouldReturnAllProductsWithoutZeroStockProducts() throws Exception {
    Mockito.when(productRepository.getProducts()).thenReturn(TestingUtils.buildProducts(true));
    var productDTOS = productService.getAllProducts();
    Assertions.assertThat(productDTOS).isNotEmpty();
    Assertions.assertThat(productDTOS).hasSize(1);

    TestingUtils.commonProductDTOAsserts(
        productDTOS.get(0), 1L, "LEGO #14362905", "100.00", "95.9", 1000L, "LEGO");
  }

  @Test
  public void shouldReturnEmptyProductListWhenRepositoryReturnEmptyProductList() throws Exception {
    Mockito.when(productRepository.getProducts()).thenReturn(Collections.emptyList());
    Assertions.assertThat(productService.getAllProducts()).isEmpty();
  }

  @Test
  public void shouldReturnEmptyProductListWhenRepositoryReturnNull() throws Exception {
    Mockito.when(productRepository.getProducts()).thenReturn(null);
    Assertions.assertThat(productService.getAllProducts()).isEmpty();
  }

  @Test
  public void shouldThrowExceptionWhenRepositoryThrowIOException() throws Exception {
    Mockito.when(productRepository.getProducts()).thenThrow(new IOException());
    Assertions.assertThatThrownBy(() -> productService.getAllProducts())
        .isInstanceOf(ProductDataException.class)
        .hasMessage("Error while reading product data");
  }

  @Test
  public void shouldThrowExceptionWhenRepositoryThrowIllegalArgumentException() throws Exception {
    Mockito.when(productRepository.getProducts()).thenThrow(new IllegalArgumentException());
    Assertions.assertThatThrownBy(() -> productService.getAllProducts())
        .isInstanceOf(ProductDataException.class)
        .hasMessage("Error while reading product data");
  }

  @Test
  public void shouldThrowExceptionWhenRepositoryThrowURISyntaxException() throws Exception {
    Mockito.when(productRepository.getProducts()).thenThrow(new URISyntaxException("test", "test"));
    Assertions.assertThatThrownBy(() -> productService.getAllProducts())
        .isInstanceOf(ProductDataException.class)
        .hasMessage("Error while reading product data");
  }
}
