package com.example.basic.basic_feature.domain.model

import android.os.Parcelable
import com.example.basic.basic_feature.data.local.enitity.CartEntity
import com.example.basic.basic_feature.data.local.enitity.FavouriteEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val productName: String? = null,
    val productPrice: String? = null,
    var productImage: List<String>? = null,
    val productOffer: String? = null,
    val productBrand: String? = null,
    val productSize: List<String>? = null,
    val productDetail: String? = null,
    val productPreviousPrice: String? = null,
): Parcelable {
    fun toFavourite(): FavouriteEntity = FavouriteEntity(
        productName, productPrice, productImage, productOffer, productBrand, productSize, productDetail, productPreviousPrice
    )

    fun toCartEntity(): CartEntity = CartEntity(
        productName, productPrice, productImage, productOffer, productBrand, productSize, productDetail, productPreviousPrice
    )

}
