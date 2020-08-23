package com.productservice.repository;

import com.productservice.config.ProductConfig;
import com.productservice.util.TestingUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ProductRepositoryTest {

  @Mock private ProductConfig productConfig;
  private ProductRepository productRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    productRepository = new ProductRepository(productConfig);
    Mockito.when(productConfig.getColumnsSeparator()).thenReturn(',');
    Mockito.when(productConfig.isIgnoreQuotation()).thenReturn(true);
    Mockito.when(productConfig.getFileName()).thenReturn("product_data.csv");
  }

  @Test
  public void shouldGetAllDataFromMainProductFile() throws Exception {
    var products = productRepository.getProducts();
    Assertions.assertThat(products).isNotEmpty();
    Assertions.assertThat(products).hasSize(523);
    products.forEach(product -> Assertions.assertThat(product.getId()).isNotNull());
    products.forEach(product -> Assertions.assertThat(product.getName()).isNotNull());
    products.forEach(product -> Assertions.assertThat(product.getPrice()).isNotNull());
    products.forEach(product -> Assertions.assertThat(product.getOldPrice()).isNotNull());
    products.forEach(product -> Assertions.assertThat(product.getStock()).isNotNull());
    products.forEach(product -> Assertions.assertThat(product.getBrand()).isNotNull());
  }

  @Test
  public void shouldGetAllDataFromLimitedProductFile() throws Exception {
    Mockito.when(productConfig.getFileName()).thenReturn("product_data_with_limited_data.csv");
    var products = productRepository.getProducts();
    Assertions.assertThat(products).isNotEmpty();
    Assertions.assertThat(products).hasSize(5);
    TestingUtils.commonProductAsserts(
        products.get(0), 18450L, "s.Oliver #1574690", "35.99", "35.99", 12L, "s.Oliver");
    TestingUtils.commonProductAsserts(
        products.get(1), 48738L, "UNDERCOVER #2462931", "4.99", "7.99", 37L, "UNDERCOVER");
    TestingUtils.commonProductAsserts(
        products.get(2), 28398L, "PLAYMOBIL #4161522", "18.99", "23.99", 34L, "PLAYMOBIL");
    TestingUtils.commonProductAsserts(
        products.get(3), 34074L, "PLAYMOBIL #6358024", "41.99", "59.99", 361L, "PLAYMOBIL");
    TestingUtils.commonProductAsserts(
        products.get(4), 15436L, "LEGO #1196398", "221.99", "299.99", 177L, "LEGO");
  }

  @Test
  public void shouldThrowExceptionWhenProductFileHasWrongHeader() {
    Mockito.when(productConfig.getFileName()).thenReturn("product_data_with_incorrect_header.csv");
    Assertions.assertThatThrownBy(() -> productRepository.getProducts())
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Error capturing CSV header");
  }

  @Test
  public void shouldThrowExceptionWhenProductFileIncorrect() {
    Mockito.when(productConfig.getFileName()).thenReturn("test.csv");
    Assertions.assertThatThrownBy(() -> productRepository.getProducts())
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void shouldThrowExceptionWhenProductFileIsNull() {
    Mockito.when(productConfig.getFileName()).thenReturn(null);
    Assertions.assertThatThrownBy(() -> productRepository.getProducts())
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void shouldThrowExceptionWhenProductFileIsEmpty() {
    Mockito.when(productConfig.getFileName()).thenReturn("  ");
    Assertions.assertThatThrownBy(() -> productRepository.getProducts())
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void shouldThrowExceptionWhenColumnSeparatorIsIncorrect() {
    Mockito.when(productConfig.getColumnsSeparator()).thenReturn(';');
    Assertions.assertThatThrownBy(() -> productRepository.getProducts())
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Error capturing CSV");
  }

  @Test
  public void shouldThrowExceptionWhenColumnSeparatorIsEmpty() {
    Mockito.when(productConfig.getColumnsSeparator()).thenReturn(' ');
    Assertions.assertThatThrownBy(() -> productRepository.getProducts())
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Error capturing CSV");
  }
}
