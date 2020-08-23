package com.productservice.service;

import com.productservice.domain.ProductDTO;
import com.productservice.exception.ProductDataException;
import com.productservice.mapper.ProductMapper;
import com.productservice.repository.ProductRepository;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public List<ProductDTO> getAllProducts() {
    try {
      var products = productRepository.getProducts();
      return products != null ? ProductMapper.toProductDTOS(products) : Collections.emptyList();
    } catch (IOException | IllegalArgumentException | URISyntaxException exception) {
      throw new ProductDataException("Error while reading product data");
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
