package com.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.domain.ProductDTO;
import com.productservice.repository.ProductRepository;
import com.productservice.service.ProductService;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIT {

  @Autowired private MockMvc mockMvc;
  @Autowired private ProductService productService;
  @Autowired private ProductRepository productRepository;
  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldThrowExceptionGettingProductsWithoutAuthentication() throws Exception {
    var mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/product")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andReturn();
    Assertions.assertThat(mvcResult.getResponse()).isNotNull();
    Assertions.assertThat(mvcResult.getResolvedException()).isNotNull();
    Assertions.assertThat(mvcResult.getResolvedException())
        .isInstanceOf(AccessDeniedException.class);
    Assertions.assertThat(mvcResult.getResolvedException().getMessage()).isNotNull();
    Assertions.assertThat(mvcResult.getResolvedException().getMessage())
        .isEqualTo("Access is denied");
  }

  @Test
  @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
  public void shouldGetAllProductsWithNonZeroStock() throws Exception {
    var mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/product")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    Assertions.assertThat(mvcResult).isNotNull();
    Assertions.assertThat(mvcResult.getResponse()).isNotNull();
    var productDTOList =
        Arrays.asList(
            objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), ProductDTO[].class));
    Assertions.assertThat(productDTOList).isNotEmpty();
    Assertions.assertThat(productDTOList).hasSize(437);

    productDTOList.forEach(product -> Assertions.assertThat(product.getName()).isNotEmpty());
    productDTOList.forEach(product -> Assertions.assertThat(product.getPrice()).isPositive());
    productDTOList.forEach(product -> Assertions.assertThat(product.getOldPrice()).isNotNegative());
    productDTOList.forEach(product -> Assertions.assertThat(product.getStock()).isGreaterThan(0L));
    productDTOList.forEach(product -> Assertions.assertThat(product.getBrand()).isNotEmpty());
  }
}
