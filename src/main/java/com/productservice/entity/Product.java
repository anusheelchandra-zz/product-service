package com.productservice.entity;

import com.opencsv.bean.CsvBindByName;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  @CsvBindByName(column = "ID", required = true)
  private Long id;

  @CsvBindByName(column = "NAME", required = true)
  private String name;

  @CsvBindByName(column = "PRICE", required = true)
  private BigDecimal price;

  @CsvBindByName(column = "OLD_PRICE", required = true)
  private BigDecimal oldPrice;

  @CsvBindByName(column = "STOCK", required = true)
  private Long stock;

  @CsvBindByName(column = "BRAND", required = true)
  private String brand;
}
