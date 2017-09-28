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

package com.sky.android.news.data.source.cloud

import com.sky.android.news.Constant
import com.sky.android.news.data.cache.ZhiHuCache
import com.sky.android.news.data.mapper.MapperFactory
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.model.StoryListModel
import com.sky.android.news.data.service.ZhiHuService
import com.sky.android.news.data.source.ZhiHuDataSource
import rx.Observable

/**
 * Created by sky on 17-9-28.
 */
class CloudZhiHuDataSouce(private val mCache: ZhiHuCache) : CloudDataSource(), ZhiHuDataSource {

    override fun getLatestStories(): Observable<StoryListModel> {

        val service = buildZhiHuService()

        return service.getLatestStories().map { it ->
            val model = MapperFactory.createStoryListMapper().transform(it)
            // 保存到本地缓存
            mCache.saveLatestStories(model)
            model
        }
    }

    override fun getStories(date: String): Observable<StoryListModel> {

        val service = buildZhiHuService()

        return service.getStories(date).map { it ->
            val model = MapperFactory.createStoryListMapper().transform(it)
            // 保存到本地缓存
            mCache.saveStories(date, model)
            model
        }
    }

    override fun getStory(id: String): Observable<StoryDetailsModel> {

        val service = buildZhiHuService()

        return service.getStory(id).map { it ->
            val model = MapperFactory.createStoryDetailsMapper().transform(it)
            // 保存到本地缓存
            mCache.saveStory(id, model)
            model
        }
    }

    private fun buildZhiHuService(): ZhiHuService {
        return buildService(ZhiHuService::class.java, Constant.Service.ZH_BASE_URL)
    }
}