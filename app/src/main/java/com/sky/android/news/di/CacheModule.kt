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

import com.sky.android.news.data.cache.CacheManager
import com.sky.android.news.data.cache.ICacheManager
import com.sky.android.news.data.cache.INewsCache
import com.sky.android.news.data.cache.IStoryCache
import com.sky.android.news.data.cache.NewsCache
import com.sky.android.news.data.cache.StoryCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    @Singleton
    abstract fun bindCacheManager(cache: CacheManager): ICacheManager

    @Binds
    @Singleton
    abstract fun bindNewsCache(cache: NewsCache): INewsCache

    @Binds
    @Singleton
    abstract fun bindStoryCache(cache: StoryCache): IStoryCache
}