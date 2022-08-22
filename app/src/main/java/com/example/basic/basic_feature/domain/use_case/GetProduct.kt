package com.example.basic.basic_feature.domain.use_case

import android.util.Log
import com.example.basic.basic_feature.data.remote.firebase.repo.FirestoreRepo
import com.example.basic.basic_feature.domain.model.ProductModel
import com.example.basic.core.utils.Constants.FASHION_BOX
import com.example.basic.core.utils.Constants.HORIZONTAL_SLIDER
import com.example.basic.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProduct(
    private val repository: FirestoreRepo
) {
    operator fun invoke(collection: String): Flow<Resource<List<ProductModel>>> {
        return when(collection) {
            HORIZONTAL_SLIDER -> {
                repository.getAllHZProduct(collection)
            }
            FASHION_BOX -> {
                repository.getAllFBProduct(collection)
            }
            else -> {
                repository.getAllCollectionProduct(collection)
            }
        }
    }
}