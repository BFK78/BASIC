package com.example.basic.basic_feature.presentation

import com.example.basic.basic_feature.domain.model.ProductModel

data class ProductState(
  val productList: List<ProductModel> = emptyList(),
  val isLoading: Boolean = false
)