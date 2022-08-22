package com.example.basic.basic_feature.data.local.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.basic.basic_feature.domain.model.ProductModel

@Entity
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val model: ProductModel
)
