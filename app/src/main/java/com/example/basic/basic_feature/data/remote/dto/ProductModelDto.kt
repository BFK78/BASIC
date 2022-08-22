package com.example.basic.basic_feature.data.remote.dto

import com.example.basic.basic_feature.data.local.enitity.CollectionEntity
import com.example.basic.basic_feature.data.local.enitity.FBEntity
import com.example.basic.basic_feature.data.local.enitity.HZEntity

data class ProductModelDto(
    val productName: String? = null,
    val productPrice: String? = null,
    val productImage: List<String>? = null,
    val productOffer: String? = null,
    val productBrand: String? = null,
    val productSize: List<String>? = null,
    val productDetail: String? = null,
    val productPreviousPrice: String? = null,
    val productId: String? = null
) {
    fun toHZEntity(): HZEntity =
        HZEntity(
            productName,
            productPrice,
            productImage,
            productOffer,
            productBrand,
            productSize,
            productDetail,
            productPreviousPrice
        )

    fun toFBEntity(): FBEntity =
        FBEntity(
            productName,
            productPrice,
            productImage,
            productOffer,
            productBrand,
            productSize,
            productDetail,
            productPreviousPrice
        )

    fun toCollectionEntity(collection: String): CollectionEntity =
        CollectionEntity(
            productName = productName,
            productPrice = productPrice,
            productImage = productImage,
            productOffer = productOffer,
            productBrand = productBrand,
            productSize = productSize,
            productDetail = productDetail,
            productPreviousPrice = productPreviousPrice,
            collection = collection
        )
}
