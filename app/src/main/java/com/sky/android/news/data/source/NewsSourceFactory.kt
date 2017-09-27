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

package com.sky.android.news.data.source

import android.content.Context
import com.sky.android.news.VApplication
import com.sky.android.news.data.cache.NewsCache
import com.sky.android.news.data.cache.impl.CacheManagerImpl
import com.sky.android.news.data.cache.impl.NewsCacheImpl
import com.sky.android.news.data.source.cloud.CloudNewsDataSource
import com.sky.android.news.data.source.disk.DiskNewsDataSource

/**
 * Created by sky on 17-9-21.
 */
class NewsSourceFactory(private val mContext: Context) {

    private var mCache: NewsCache = NewsCacheImpl(CacheManagerImpl.getInstance(mContext))

    fun create(): NewsDataSource {
        return createRemoteSource()
    }

    fun createLocalSource(): NewsDataSource {
        return DiskNewsDataSource(mContext, mCache)
    }

    fun createRemoteSource(): NewsDataSource {
        return CloudNewsDataSource(mCache)
    }
}