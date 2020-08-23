package com.productservice;

import com.productservice.config.ProductConfig;
import com.productservice.controller.ProductController;
import com.productservice.repository.ProductRepository;
import com.productservice.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceApplicationTests {

  @Autowired private ProductController productController;
  @Autowired private ProductService productService;
  @Autowired private ProductRepository productRepository;
  @Autowired private ProductConfig productConfig;

  @Test
  public void shouldLoadContexts() {
    Assertions.assertThat(productController).isNotNull();
    Assertions.assertThat(productService).isNotNull();
    Assertions.assertThat(productRepository).isNotNull();
    Assertions.assertThat(productConfig).isNotNull();
    Assertions.assertThat(productConfig.getFileName()).isEqualTo("product_data.csv");
    Assertions.assertThat(productConfig.getColumnsSeparator()).isEqualTo(',');
    Assertions.assertThat(productConfig.isIgnoreQuotation()).isTrue();
  }
}
