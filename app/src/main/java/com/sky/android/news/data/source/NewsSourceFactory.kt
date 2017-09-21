package com.sky.android.news.data.source

import android.content.Context
import com.sky.android.news.VApplication
import com.sky.android.news.data.cache.NewsCache
import com.sky.android.news.data.cache.impl.NewsCacheImpl
import com.sky.android.news.data.source.cloud.CloudNewsDataSource
import com.sky.android.news.data.source.disk.DiskNewsDataSource

/**
 * Created by sky on 17-9-21.
 */
class NewsSourceFactory(private val mContext: Context) {

    private var mCache: NewsCache = NewsCacheImpl(VApplication.getCacheManager()!!)

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