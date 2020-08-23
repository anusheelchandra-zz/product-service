package com.productservice.repository;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.productservice.config.ProductConfig;
import com.productservice.entity.Product;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepository {

  private final ProductConfig productConfig;

  public List<Product> getProducts() throws Exception {
    Reader reader = new BufferedReader(getProductFilAsStream());
    var productCsvBuilder = getProductCsvBuilder(reader);
    var products = productCsvBuilder.parse();
    reader.close();
    return Collections.unmodifiableList(products);
  }

  private CsvToBean<Product> getProductCsvBuilder(Reader reader) {
    var mappingStrategy = new HeaderColumnNameMappingStrategy<Product>();
    mappingStrategy.setType(Product.class);
    return new CsvToBeanBuilder<Product>(reader)
        .withType(Product.class)
        .withMappingStrategy(mappingStrategy)
        .withSeparator(productConfig.getColumnsSeparator())
        .withIgnoreQuotations(productConfig.isIgnoreQuotation())
        .build();
  }

  private InputStreamReader getProductFilAsStream() {
    var fileName = productConfig.getFileName();
    return new InputStreamReader(
        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(fileName)));
  }
}
