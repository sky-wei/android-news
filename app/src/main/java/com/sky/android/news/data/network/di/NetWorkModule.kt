/*
 * Copyright (c) 2024 The sky Authors.
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

package com.sky.android.news.data.network.di

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sky.android.news.Constant
import com.sky.android.news.data.network.NewsInterceptor
import com.sky.android.news.data.service.INewsService
import com.sky.android.news.data.service.IStoryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by sky on 2021-03-12.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
                .newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(NewsInterceptor())
                .build()
    }

    @Provides
    fun provideNewsService(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory,
        callAdapterFactory: CoroutineCallAdapterFactory
    ): INewsService {
        return Retrofit.Builder()
            .baseUrl(Constant.Service.NEWS_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
            .create(INewsService::class.java)
    }

    @Provides
    fun provideStoryService(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory,
        callAdapterFactory: CoroutineCallAdapterFactory
    ): IStoryService {
        return Retrofit.Builder()
            .baseUrl(Constant.Service.STORY_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
            .create(IStoryService::class.java)
    }

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    fun provideCoroutineCallAdapterFactory(): CoroutineCallAdapterFactory =
        CoroutineCallAdapterFactory()
}