package com.sky.android.news.data.source.cloud

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import com.sky.android.common.utils.Alog
import com.sky.android.news.data.news.HeadLine
import com.sky.android.news.data.news.LineItem
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        return retrofit.create(tClass)
    }

    private fun newGosn(): Gson {

        return GsonBuilder().registerTypeAdapter(
                HeadLine::class.java, JsonDeserializer<HeadLine> { json, _, context ->

            val model = HeadLine(listOf())

            json.asJsonObject.entrySet().forEach {

                if (it.key.startsWith("T")) {
                    // 转换
                    model.lineItems = context.deserialize<List<LineItem>>(
                            it.value, object: TypeToken<List<LineItem>>() {}.type)
                }
            }

            model
        }).create()
    }

    class RequestInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain?): Response {

            var request = chain!!.request()

            val url = request.url().url().path

            Alog.d("RequestUrl: $url")

            request = request.newBuilder()
                    .addHeader("deviceplatform", "android")
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0")
                    .build()

            return chain.proceed(request)
        }
    }
}