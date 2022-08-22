package com.example.basic.basic_feature.data.util

import com.google.gson.Gson
import java.lang.reflect.Type

class GsonParser(
    private val gson: Gson
): JsonParser {

    override fun <T> fromJson(data: String, type: Type): T? {
        return gson.fromJson(
            data,
            type
        )
    }

    override fun <T> toJson(data: T, type: Type): String? {
        return gson.toJson(
            data,
            type
        )
    }
}