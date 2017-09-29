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

package com.sky.android.news.data.cache.impl

import com.sky.android.news.data.cache.CacheManager
import com.sky.android.news.data.cache.StoryCache
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.model.StoryDetailsPackageModel
import com.sky.android.news.data.model.StoryListModel
import com.sky.android.news.data.model.StoryListPackageModel

/**
 * Created by sky on 17-9-28.
 */
class StoryCacheImpl(private val mCacheManager: CacheManager) : StoryCache {

    private var mLatestStoriesKey = mCacheManager.buildKey(
            StoryCacheImpl::class.java.name + ":getLatestStories()")

    override fun getLatestStories(): StoryListModel? {

        val model = mCacheManager.get(mLatestStoriesKey, StoryListPackageModel::class.java)

        if (model != null
                && !isExpired(model.lastTime, 1000 * 60 * 60)) {
            // 返回缓存数据
            return model.model
        }
        return null
    }

    override fun saveLatestStories(model: StoryListModel) {
        mCacheManager.put(mLatestStoriesKey,
                StoryListPackageModel(System.currentTimeMillis(), model))
    }

    override fun getStories(date: String): StoryListModel? {

        val model = mCacheManager.get(
                mCacheManager.buildKey(date), StoryListPackageModel::class.java)

        if (model != null
                && !isExpired(model.lastTime, 1000 * 60 * 60)) {
            // 返回缓存数据
            return model.model
        }
        return null
    }

    override fun saveStories(date: String, model: StoryListModel) {
        mCacheManager.put(mCacheManager.buildKey(date),
                StoryListPackageModel(System.currentTimeMillis(), model))
    }

    override fun getStory(id: String): StoryDetailsModel? {

        val model = mCacheManager.get(
                mCacheManager.buildKey(id), StoryDetailsPackageModel::class.java)

        if (model != null
                && !isExpired(model.lastTime, 1000 * 60 * 60 * 12)) {
            // 返回缓存数据
            return model.model
        }
        return null
    }

    override fun saveStory(id: String, model: StoryDetailsModel) {
        mCacheManager.put(mCacheManager.buildKey(id),
                StoryDetailsPackageModel(System.currentTimeMillis(), model))
    }

    private fun isExpired(lastTime: Long, timeout: Long): Boolean {

        val curTime = System.currentTimeMillis()

        // 当前时间-最后时间>=超时时间 || 异常情况: 当前时间 < 最后时间
        return curTime - lastTime >= timeout || curTime < lastTime
    }
}