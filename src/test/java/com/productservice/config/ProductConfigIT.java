package com.productservice.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductConfigIT {

  @Autowired private ProductConfig productConfig;

  @Test
  public void shouldLoadProductConfig() {
    Assertions.assertThat(productConfig).isNotNull();
    Assertions.assertThat(productConfig.getFileName()).isEqualTo("product_data.csv");
    Assertions.assertThat(productConfig.getColumnsSeparator()).isEqualTo(',');
    Assertions.assertThat(productConfig.isIgnoreQuotation()).isTrue();
  }
}
