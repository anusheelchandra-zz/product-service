package com.productservice.controller;

import com.productservice.domain.ProductDTO;
import com.productservice.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(value = "Product Controller")
public class ProductController {

  private final ProductService productService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Product Controller to get all products")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Products returned")})
  public ResponseEntity<List<ProductDTO>> getAllProducts() {
    return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
  }
}
