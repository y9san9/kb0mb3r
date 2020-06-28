package com.y9san9.b0mb3r.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken


val gson = Gson()

fun Any?.jsonify(): String {
    return gson.toJson(this)
}

fun <T> String.fromJson() : T {
    return gson.fromJson(this, object : TypeToken<T>(){}.type)
}

fun String.asJsonObjectOrNull() : JsonObject? {
    return try {
        JsonParser.parseString(this).asJsonObject
    } catch (e: Exception){
        return null
    }
}

fun String.asJsonObject() : JsonObject {
    return asJsonObjectOrNull()!!
}
