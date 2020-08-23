package com.productservice.mapper;

import com.productservice.domain.ProductDTO;
import com.productservice.entity.Product;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {

  public List<ProductDTO> toProductDTOS(Collection<Product> products) {
    return products.stream()
        .filter(Objects::nonNull)
        .filter(product -> product.getStock() != 0)
        .map(ProductMapper::toProductDTO)
        .collect(Collectors.toUnmodifiableList());
  }

  private ProductDTO toProductDTO(Product product) {
    return ProductDTO.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .oldPrice(product.getOldPrice())
        .stock(product.getStock())
        .brand(product.getBrand())
        .build();
  }
}
