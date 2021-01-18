/*
 * Copyright (c) 2021 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.android.news.data.service

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sky.android.news.Constant
import com.sky.android.news.data.DataException
import com.sky.android.news.data.news.Content
import com.sky.android.news.data.news.Details
import com.sky.android.news.data.news.HeadLine
import com.sky.android.news.data.news.LineItem
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

/**
 * Created by sky on 2021-01-06.
 */
class ServiceFactory : IServiceFactory {

    override fun <T> createService(tClass: Class<T>): T =
            createService(tClass, Constant.Service.BASE_URL)

    override fun <T> createService(tClass: Class<T>, baseUrl: String): T =
            createRetrofit(createHttpClient(arrayListOf(baseUrl)), baseUrl).create(tClass)

    /**
     * 创建OkHttpClient
     */
    private fun createHttpClient(baseUrls: List<String>): OkHttpClient {
        return OkHttpClient()
                .newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(NewsInterceptor())
                .build()
    }

    /**
     * 创建Retrofit
     */
    private fun createRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(newGosn()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    private fun newGosn(): Gson {
        return GsonBuilder()
                .registerTypeAdapter(HeadLine::class.java, HeadLineJsonDeserializer())
                .registerTypeAdapter(Details::class.java, DetailsJsonDeserializer())
                .create()
    }

    class HeadLineJsonDeserializer : JsonDeserializer<HeadLine> {

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): HeadLine {

            json.asJsonObject.entrySet().forEach {
                // 转换
                return HeadLine(context.deserialize(
                        it.value, object: TypeToken<List<LineItem>>() {}.type))
            }
            throw DataException("解析信息异常")
        }
    }

    class DetailsJsonDeserializer : JsonDeserializer<Details> {

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Details {

            json.asJsonObject.entrySet().forEach {
                // 转换
                return Details(context.deserialize(
                        it.value, object: TypeToken<Content>() {}.type))
            }
            throw DataException("解析信息异常")
        }
    }
}