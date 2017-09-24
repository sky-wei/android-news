package com.sky.android.news.base

/**
 * Created by sky on 17-9-24.
 */

fun Any?.toExtString(): String {
    return this?.toString() ?: ""
}