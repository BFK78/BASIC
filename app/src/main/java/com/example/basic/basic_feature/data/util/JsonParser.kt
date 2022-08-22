package com.example.basic.basic_feature.data.util

import java.lang.reflect.Type

interface JsonParser {

    fun <T> fromJson(data: String, type: Type): T?

    fun <T> toJson(data: T, type: Type): String?
}