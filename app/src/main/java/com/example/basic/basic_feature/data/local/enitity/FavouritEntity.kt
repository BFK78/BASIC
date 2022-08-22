package com.example.basic.basic_feature.data.local.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.basic.basic_feature.domain.model.ProductModel
import com.example.basic.core.utils.Constants.FAVOURITES_TABLE

@Entity(tableName = FAVOURITES_TABLE)
data class FavouriteEntity (
    val productName: String? = null,
    val productPrice: String? = null,
    val productImage: List<String>? = null,
    val productOffer: String? = null,
    val productBrand: String? = null,
    val productSize: List<String>? = null,
    val productDetail: String? = null,
    val productPreviousPrice: String? = null,
    val collection: String? = null,
    @PrimaryKey val id: Int? = null
) {
    fun toProductModel(): ProductModel = ProductModel(
        productName = productName,
        productPrice = productPrice,
        productImage = productImage,
        productOffer = productOffer,
        productBrand = productBrand,
        productSize = productSize,
        productDetail = productDetail,
        productPreviousPrice = productPreviousPrice
    )
}