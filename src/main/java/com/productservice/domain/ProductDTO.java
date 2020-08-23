package com.productservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

  @JsonIgnore private Long id; // Can be made part of response by removing @JsonIgnore

  @NonNull
  @ApiModelProperty(notes = "Name of the product")
  private String name;

  @NonNull
  @ApiModelProperty(notes = "Price of the product")
  private BigDecimal price;

  @NonNull
  @ApiModelProperty(notes = "Old price of the product")
  private BigDecimal oldPrice;

  @NonNull
  @ApiModelProperty(notes = "Stock of the product")
  private Long stock;

  @NonNull
  @ApiModelProperty(notes = "Brand of the product")
  private String brand;
}
