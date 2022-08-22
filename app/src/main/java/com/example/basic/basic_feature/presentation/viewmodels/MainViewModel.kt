package com.example.basic.basic_feature.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic.basic_feature.data.local.enitity.CartEntity
import com.example.basic.basic_feature.data.local.enitity.OrderEntity
import com.example.basic.basic_feature.domain.use_case.GetAllCartItemsUseCase
import com.example.basic.basic_feature.domain.use_case.GetAllOrdersUseCase
import com.example.basic.basic_feature.domain.use_case.InsertOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val insertOrderUseCase: InsertOrderUseCase,
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase
): ViewModel() {


    private val _orderItemsState = mutableStateOf(emptyList<OrderEntity>())
    val orderItemState: State<List<OrderEntity>> = _orderItemsState


    init {

        getAllOrderItems()

    }

    fun insertItemsToOrder() = viewModelScope.launch {
        getAllCartItemsUseCase().collectLatest {
            val orderList = it.map { cart ->
                cart.toOrderEntity()
            }
            insertOrderUseCase(orderEntity = orderList)
        }
    }

    private fun getAllOrderItems() = viewModelScope.launch {
        getAllOrdersUseCase().collectLatest {
            _orderItemsState.value = it
        }
    }

}