package com.example.basic.core.utils

import android.os.Bundle
import androidx.navigation.NavType
import com.example.basic.basic_feature.domain.model.ProductModel
import com.google.gson.Gson

class CustomNavType() : NavType<ProductModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ProductModel? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): ProductModel {
        return Gson().fromJson(value, ProductModel::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: ProductModel) {
        bundle.putParcelable(key, value)
    }
}