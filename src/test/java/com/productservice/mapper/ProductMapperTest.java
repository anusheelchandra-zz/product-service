package com.productservice.mapper;

import com.productservice.domain.ProductDTO;
import com.productservice.util.TestingUtils;
import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductMapperTest {

  @Test
  public void shouldMapToProductDTOs() {
    var productDTOS = ProductMapper.toProductDTOS(TestingUtils.buildProducts(false));
    Assertions.assertThat(productDTOS).isNotEmpty();
    Assertions.assertThat(productDTOS).hasSize(2);
    TestingUtils.commonProductDTOAsserts(
        productDTOS.get(0), 1L, "LEGO #14362905", "100.00", "95.9", 1000L, "LEGO");
    TestingUtils.commonProductDTOAsserts(
        productDTOS.get(1), 10920L, "s.Oliver #744328", "23.99", "23.99", 10L, "s.Oliver");
  }

  @Test
  public void shouldMapToProductDTOsAndFilterProductsWithZeroStock() {
    var productDTOS = ProductMapper.toProductDTOS(TestingUtils.buildProducts(true));
    Assertions.assertThat(productDTOS).isNotEmpty();
    Assertions.assertThat(productDTOS).hasSize(1);
    TestingUtils.commonProductDTOAsserts(
        productDTOS.get(0), 1L, "LEGO #14362905", "100.00", "95.9", 1000L, "LEGO");
  }

  private void commonAsserts(ProductDTO productDTO) {
    Assertions.assertThat(productDTO.getId()).isEqualTo(1L);
    Assertions.assertThat(productDTO.getName()).isEqualTo("LEGO #14362905");
    Assertions.assertThat(productDTO.getPrice()).isEqualTo(new BigDecimal("100.00"));
    Assertions.assertThat(productDTO.getOldPrice()).isEqualTo(new BigDecimal("95.9"));
    Assertions.assertThat(productDTO.getStock()).isEqualTo(1000L);
    Assertions.assertThat(productDTO.getBrand()).isEqualTo("LEGO");
  }
}
