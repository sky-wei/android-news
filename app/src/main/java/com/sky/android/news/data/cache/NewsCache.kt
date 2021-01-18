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

package com.sky.android.news.data.cache

import com.sky.android.news.data.model.*

/**
 * Created by sky on 17-9-21.
 */
class NewsCache(private val mCacheManager: ICacheManager) : INewsCache {

    private var mCategoryKey = mCacheManager.buildKey(
            NewsCache::class.java.name + ":getCategory()")

    override fun getCategory(): CategoryModel? =
            mCacheManager.get(mCategoryKey, CategoryModel::class.java)

    override fun saveCategory(model: CategoryModel) {
        mCacheManager.put(mCategoryKey, model)
    }

    override fun getHeadLine(tid: String, start: Int, end: Int): HeadLineModel? {

        val key = mCacheManager.buildKey("$tid-$start-$end")
        val model = mCacheManager.get(key, LinePackageModel::class.java)

        if (model != null
                && !isExpired(model.lastTime, 1000 * 60 * 10)) {
            // 返回缓存数据
            return model.model
        }
        return null
    }

    override fun saveHeadLine(tid: String, start: Int, end: Int, model: HeadLineModel) {
        mCacheManager.put(
                mCacheManager.buildKey("$tid-$start-$end"),
                LinePackageModel(System.currentTimeMillis(), model))
    }

    override fun getDetails(docId: String): DetailsModel? {

        val key = mCacheManager.buildKey(docId)
        var model = mCacheManager.get(key, DetailsPackageModel::class.java)

        if (model != null
                && !isExpired(model.lastTime, 1000 * 60 * 60 * 24)) {
            // 返回缓存数据
            return model.model
        }
        return null
    }

    override fun saveDetails(docId: String, model: DetailsModel) {
        mCacheManager.put(
                mCacheManager.buildKey(docId),
                DetailsPackageModel(System.currentTimeMillis(), model))
    }

    private fun isExpired(lastTime: Long, timeout: Long): Boolean {

        val curTime = System.currentTimeMillis()

        // 当前时间-最后时间>=超时时间 || 异常情况: 当前时间 < 最后时间
        return curTime - lastTime >= timeout || curTime < lastTime
    }
}