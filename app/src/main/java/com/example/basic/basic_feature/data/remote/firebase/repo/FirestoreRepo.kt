package com.example.basic.basic_feature.data.remote.firebase.repo

import com.example.basic.basic_feature.data.remote.dto.ProductModelDto
import com.example.basic.basic_feature.domain.model.ProductModel
import com.example.basic.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FirestoreRepo {

    fun getAllHZProduct(collection: String): Flow<Resource<List<ProductModel>>>

    fun getAllFBProduct(collection: String): Flow<Resource<List<ProductModel>>>

    fun getAllCollectionProduct(collection: String): Flow<Resource<List<ProductModel>>>
}