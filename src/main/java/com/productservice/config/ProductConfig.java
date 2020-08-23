package com.productservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "product")
public class ProductConfig {

  private String fileName;
  private char columnsSeparator;
  private boolean ignoreQuotation;
}
