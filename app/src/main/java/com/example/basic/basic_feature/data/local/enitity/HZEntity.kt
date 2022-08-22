package com.example.basic.basic_feature.data.local.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.basic.basic_feature.data.remote.firebase.util.FirebaseConstants.SLIDER
import com.example.basic.basic_feature.domain.model.ProductModel

@Entity(tableName = SLIDER)
data class HZEntity(
    val productName: String? = null,
    val productPrice: String? = null,
    val productImage: List<String>? = null,
    val productOffer: String? = null,
    val productBrand: String? = null,
    val productSize: List<String>? = null,
    val productDetail: String? = null,
    val productPreviousPrice: String? = null,
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