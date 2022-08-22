package com.example.basic.basic_feature.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.basic.basic_feature.data.util.JsonParser
import com.example.basic.basic_feature.domain.model.ProductModel
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
   private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromListToJson(list: List<String>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<String>>(){}.type
        )?: "[]"
    }

    @TypeConverter
    fun fromJsonToList(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        )?: emptyList()
    }

    @TypeConverter
    fun fromModelToJson(productModel: ProductModel): String {
        return jsonParser.toJson(
            productModel,
            object : TypeToken<ProductModel>(){}.type
        ) ?: ""
    }

    @TypeConverter
    fun fromJsonToModel(json: String): ProductModel {
        return jsonParser.fromJson<ProductModel>(
            json,
            object : TypeToken<ProductModel>(){}.type
        ) ?: ProductModel()
    }
}