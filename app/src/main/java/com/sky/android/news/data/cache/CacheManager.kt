package com.sky.android.news.data.cache

/**
 * Created by sky on 17-9-21.
 */
interface CacheManager {

    fun <T> get(key: String, tClass: Class<T>): T?

    fun <T> put(key: String, value: T): Boolean

    fun remove(key: String): Boolean

    fun close()

    fun buildKey(value: String): String
}