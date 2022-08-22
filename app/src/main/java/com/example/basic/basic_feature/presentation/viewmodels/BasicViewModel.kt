package com.example.basic.basic_feature.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.basic.basic_feature.data.remote.firebase.repo.FirebaseUser
import com.example.basic.basic_feature.domain.model.ProductModel
import com.example.basic.basic_feature.domain.use_case.*
import com.example.basic.basic_feature.presentation.ProductState
import com.example.basic.core.utils.LoadingState
import com.example.basic.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class BasicViewModel @Inject constructor(
    private val getProduct: GetProduct,
    private val firebaseUser: FirebaseUser,
    private val getFavourite: GetFavourite,
    private val insertFavourite: InsertFavourite,
    private val removeFavourite: RemoveFavourite,
    private val isFavourite: IsFavourite,
    private val addUsernameUseCase: AddUsernameUseCase
): ViewModel() {

    private val _loadingState = MutableStateFlow(LoadingState(null))
    val loadingState: StateFlow<LoadingState> = _loadingState

    private val _state = mutableStateOf(ProductState())
    val state: State<ProductState> = _state

    private val _fbState = MutableStateFlow(ProductState())
    val fbState: StateFlow<ProductState> = _fbState

    private val _tState = MutableStateFlow(ProductState())
    val tState: StateFlow<ProductState> = _tState

    fun getHzData(collection: String) = viewModelScope.launch {
        getProduct(collection = collection)
            .onEach {
                when(it) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            productList = it.data?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            productList = it.data?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            productList = it.data?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
    }

    fun getFbData(collection: String) = viewModelScope.launch {
        getProduct(collection = collection)
            .onEach {
                when(it) {
                    is Resource.Loading -> {
                        _fbState.value = fbState.value.copy(
                            productList = it.data?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _fbState.value = fbState.value.copy(
                            productList = it.data?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Success -> {
                        _fbState.value = fbState.value.copy(
                            productList = it.data?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
    }

    fun getTData(collection: String) = viewModelScope.launch {
        getProduct(collection = collection)
            .onEach {
                when(it) {
                    is Resource.Loading -> {
                        _tState.value = tState.value.copy(
                            productList = it.data?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _tState.value = tState.value.copy(
                            productList = it.data?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Success -> {
                        _tState.value = tState.value.copy(
                            productList = it.data?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        Log.i("hz login", "$email  $password")
        _loadingState.value = LoadingState.LOADING
        try {
            firebaseUser.userSignIn(email, password)
        }catch (e: Exception) {
            Log.i("hz", e.message.toString())
            _loadingState.value = LoadingState.error(e.message.toString())
        }
        _loadingState.value = LoadingState.DONE
    }

    fun createUser(email: String, password: String, username: String) = viewModelScope.launch {
        _loadingState.value = LoadingState.LOADING
        try {
            firebaseUser.userRegister(email, password)

        }catch (e: Exception) {
            Log.i("hz", e.message.toString())
            _loadingState.value = LoadingState.error(e.message.toString())
        }

        if (_loadingState.value == LoadingState.LOADING) {
            _loadingState.value = LoadingState.DONE
            addUsername(username = username)
        }
    }

    suspend fun getAllFavourite(): List<ProductModel> {
       return getFavourite.invoke()
    }

    suspend fun insertFavourite(favouriteEntity: ProductModel) {
        insertFavourite.invoke(favourite = favouriteEntity)
    }

    suspend fun removeFavourite(favouriteEntity: String) {
        removeFavourite.invoke(favourite = favouriteEntity)
    }

    suspend fun isFavourite(productName: String): Boolean {
        return isFavourite.invoke(productName = productName)
    }

    private fun addUsername(username: String) = viewModelScope.launch {
        addUsernameUseCase(username = username)
    }
}
