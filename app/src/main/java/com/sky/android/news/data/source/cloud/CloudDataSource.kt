package com.sky.android.news.data.source.cloud

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by sky on 17-9-21.
 */
abstract class CloudDataSource {

    fun <T> buildService(tClass: Class<T>, baseUrl: String): T {

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        return retrofit.create(tClass)
    }
}