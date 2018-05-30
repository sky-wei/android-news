/*
 * Copyright (c) 2017 The sky Authors.
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

package com.sky.android.news.data.source.cloud

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.sky.android.common.utils.Alog
import com.sky.android.news.data.DataException
import com.sky.android.news.data.news.Content
import com.sky.android.news.data.news.Details
import com.sky.android.news.data.news.HeadLine
import com.sky.android.news.data.news.LineItem
import io.reactivex.ObservableEmitter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.reactivestreams.Subscriber
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

/**
 * Created by sky on 17-9-21.
 */
abstract class CloudDataSource {

    fun <T> buildService(tClass: Class<T>, baseUrl: String): T {

        val client = OkHttpClient()
                .newBuilder()
                .addInterceptor(RequestInterceptor())
                .build()

        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(newGosn()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(tClass)
    }

    private fun newGosn(): Gson {
        return GsonBuilder()
                .registerTypeAdapter(HeadLine::class.java, HeadLineJsonDeserializer())
                .registerTypeAdapter(Details::class.java, DetailsJsonDeserializer())
                .create()
    }

    fun <T> handler(observableEmitter: ObservableEmitter<in T>, model: T?) {

        try {
            if (model != null) {
                // 处理下一步
                observableEmitter.onNext(model)
            }

            // 完成
            observableEmitter.onComplete()
        } catch (e: Throwable) {
            // 出错了
            observableEmitter.onError(e)
        }
    }

    class RequestInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain?): Response {

            var request = chain!!.request()

            val url = request.url().url()

            Alog.d("RequestUrl: $url")

            request = request.newBuilder()
                    .addHeader("deviceplatform", "android")
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0")
                    .build()

            return chain.proceed(request)
        }
    }

    class HeadLineJsonDeserializer : JsonDeserializer<HeadLine> {

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): HeadLine {

            json.asJsonObject.entrySet().forEach {
                // 转换
                return HeadLine(context.deserialize<List<LineItem>>(
                        it.value, object: TypeToken<List<LineItem>>() {}.type))
            }
            throw DataException("解析信息异常")
        }
    }

    class DetailsJsonDeserializer : JsonDeserializer<Details> {

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Details {

            json.asJsonObject.entrySet().forEach {
                // 转换
                return Details(context.deserialize<Content>(
                        it.value, object: TypeToken<Content>() {}.type))
            }
            throw DataException("解析信息异常")
        }
    }
}