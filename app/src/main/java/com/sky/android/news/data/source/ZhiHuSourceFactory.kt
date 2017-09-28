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
import com.sky.android.news.data.cache.ZhiHuCache
import com.sky.android.news.data.cache.impl.CacheManagerImpl
import com.sky.android.news.data.cache.impl.ZhiHuCacheImpl
import com.sky.android.news.data.source.cloud.CloudZhiHuDataSouce
import com.sky.android.news.data.source.disk.DiskZhiHuDataSouce

/**
 * Created by sky on 17-9-28.
 */
class ZhiHuSourceFactory(private val mContext: Context) {

    private var mCache: ZhiHuCache = ZhiHuCacheImpl(CacheManagerImpl.getInstance(mContext))

    fun create(): ZhiHuDataSource {
        return createRemoteSource()
    }

    fun createLocalSource(): ZhiHuDataSource {
        return DiskZhiHuDataSouce(mContext, mCache)
    }

    fun createRemoteSource(): ZhiHuDataSource {
        return CloudZhiHuDataSouce(mCache)
    }
}