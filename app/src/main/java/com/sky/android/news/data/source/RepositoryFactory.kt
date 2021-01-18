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

package com.sky.android.news.data.source

import android.content.Context
import com.sky.android.news.data.cache.*
import com.sky.android.news.data.service.IServiceFactory
import com.sky.android.news.data.service.ServiceFactory
import com.sky.android.news.data.source.local.NewsLocalSource
import com.sky.android.news.data.source.local.StoryLocalSource
import com.sky.android.news.data.source.remote.NewsRemoteSource
import com.sky.android.news.data.source.remote.StoryRemoteSource

/**
 * Created by sky on 2021-01-06.
 */
class RepositoryFactory(
        private val mContext: Context
) : IRepositoryFactory {

    companion object {

        fun create(context: Context): IRepositoryFactory = RepositoryFactory(context)
    }

    private val mServiceFactory: IServiceFactory by lazy { ServiceFactory() }

    private val mNewsCache: INewsCache by lazy { NewsCache(CacheManager.getInstance(mContext)) }
    private val mStoryCache: IStoryCache by lazy { StoryCache(CacheManager.getInstance(mContext)) }

    override fun createNewsSource(): INewsSource {
        return NewsRepository(
                NewsLocalSource(mNewsCache),
                NewsRemoteSource(mServiceFactory, mNewsCache)
        )
    }

    override fun createStorySource(): IStorySource {
        return StoryRepository(
                StoryLocalSource(mStoryCache),
                StoryRemoteSource(mServiceFactory, mStoryCache)
        )
    }
}