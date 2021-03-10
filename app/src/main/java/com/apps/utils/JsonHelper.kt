package com.apps.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonHelper {
    inline fun <reified T> toList(data: String?) : List<T> {
        if (data.isNullOrBlank()) return emptyList()
        val type = Types.newParameterizedType(List::class.java, T::class.java)
        val builder = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<List<T>> = builder.adapter(type)
        return adapter.nullSafe().fromJson(data) ?: emptyList()
    }

    inline fun <reified T> toModel(data: String?) : T? {
        if (data.isNullOrBlank()) return null
        val builder = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<T> = builder.adapter(T::class.java)
        return adapter.nullSafe().fromJson(data)
    }

    inline fun <reified T> toJson(data: T) : String {
        val builder = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<T> = builder.adapter(T::class.java)
        return adapter.nullSafe().toJson(data)
    }
}