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

package com.sky.android.news.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.sky.android.news.data.DataException
import com.sky.android.news.data.news.Content
import com.sky.android.news.data.news.Details
import com.sky.android.news.data.news.HeadLine
import com.sky.android.news.data.news.LineItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.lang.reflect.Type

@Module
@InstallIn(SingletonComponent::class)
object GsonModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(HeadLine::class.java, HeadLineJsonDeserializer())
            .registerTypeAdapter(Details::class.java, DetailsJsonDeserializer())
            .create()
    }


    class HeadLineJsonDeserializer : JsonDeserializer<HeadLine> {

        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): HeadLine {
            json.asJsonObject.entrySet().forEach {
                // 转换
                return HeadLine(context.deserialize(
                    it.value, object: TypeToken<List<LineItem>>() {}.type))
            }
            throw DataException("解析信息异常")
        }
    }


    class DetailsJsonDeserializer : JsonDeserializer<Details> {

        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): Details {
            json.asJsonObject.entrySet().forEach {
                // 转换
                return Details(context.deserialize(
                    it.value, object: TypeToken<Content>() {}.type))
            }
            throw DataException("解析信息异常")
        }
    }
}