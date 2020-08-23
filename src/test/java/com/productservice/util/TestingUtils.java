package com.productservice.util;

import com.productservice.domain.ProductDTO;
import com.productservice.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.assertj.core.api.Assertions;

@UtilityClass
public class TestingUtils {

  public void commonProductDTOAsserts(
      ProductDTO productDTO,
      Long id,
      String name,
      String price,
      String oldPrice,
      Long stock,
      String brand) {
    Assertions.assertThat(productDTO.getId()).isEqualTo(id);
    Assertions.assertThat(productDTO.getName()).isEqualTo(name);
    Assertions.assertThat(productDTO.getPrice()).isEqualTo(new BigDecimal(price));
    Assertions.assertThat(productDTO.getOldPrice()).isEqualTo(new BigDecimal(oldPrice));
    Assertions.assertThat(productDTO.getStock()).isEqualTo(stock);
    Assertions.assertThat(productDTO.getBrand()).isEqualTo(brand);
  }

  public void commonProductAsserts(
      Product product,
      Long id,
      String name,
      String price,
      String oldPrice,
      Long stock,
      String brand) {
    Assertions.assertThat(product.getId()).isEqualTo(id);
    Assertions.assertThat(product.getName()).isEqualTo(name);
    Assertions.assertThat(product.getPrice()).isEqualTo(new BigDecimal(price));
    Assertions.assertThat(product.getOldPrice()).isEqualTo(new BigDecimal(oldPrice));
    Assertions.assertThat(product.getStock()).isEqualTo(stock);
    Assertions.assertThat(product.getBrand()).isEqualTo(brand);
  }

  public List<Product> buildProducts(boolean withZeroStock) {
    return List.of(
        Product.builder()
            .id(1L)
            .name("LEGO #14362905")
            .price(new BigDecimal("100.00"))
            .oldPrice(new BigDecimal("95.9"))
            .stock(1000L)
            .brand("LEGO")
            .build(),
        Product.builder()
            .id(10920L)
            .name("s.Oliver #744328")
            .price(new BigDecimal("23.99"))
            .oldPrice(new BigDecimal("23.99"))
            .stock(withZeroStock ? 0L : 10L)
            .brand("s.Oliver")
            .build());
  }
}
