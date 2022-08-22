package com.example.basic.basic_feature.presentation.screens.cart_screen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic.basic_feature.data.local.enitity.CartEntity
import com.example.basic.basic_feature.domain.use_case.DeleteCartItemUseCase
import com.example.basic.basic_feature.domain.use_case.GetAllCartItemsUseCase
import com.example.basic.basic_feature.domain.use_case.InsertCartItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val insertCartItemUseCase: InsertCartItemUseCase
): ViewModel() {

    private val _cartState = mutableStateOf(emptyList<CartEntity>())
    val cartState: State<List<CartEntity>> = _cartState

    init {
        getAllCartItem()
    }

    private fun getAllCartItem() = viewModelScope.launch {
        getAllCartItemsUseCase().collectLatest {
            _cartState.value = it
        }
    }

    fun insertCartItem(cartEntity: CartEntity) = viewModelScope.launch {
        insertCartItemUseCase(cartEntity = cartEntity)
    }

    fun deleteCartItem(cartEntity: CartEntity) = viewModelScope.launch {
        deleteCartItemUseCase(cartEntity = cartEntity)
    }

    fun calculateTotalPrice(): String {
        var sum = 0
        if (cartState.value.isNotEmpty()) {
            cartState.value.forEach {
                sum += (it.productPrice?.toInt() ?: 0)
            }
        }

        return sum.toString()
    }

    fun calculateTotalDiscount(): String {
        var sum = 0
        if (cartState.value.isNotEmpty()) {
            cartState.value.forEach {
                sum += it.productOffer?.toInt() ?: 0
            }
        }

        return sum.toString()
    }

}


