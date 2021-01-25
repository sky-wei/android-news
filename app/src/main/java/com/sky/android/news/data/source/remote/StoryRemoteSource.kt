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

package com.sky.android.news.data.source.remote

import com.sky.android.news.Constant
import com.sky.android.news.data.cache.IStoryCache
import com.sky.android.news.data.mapper.MapperFactory
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.model.StoryListModel
import com.sky.android.news.data.model.XResult
import com.sky.android.news.data.service.IServiceFactory
import com.sky.android.news.data.service.IStoryService
import com.sky.android.news.data.source.IStorySource
import com.sky.android.news.ext.flowOfResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by sky on 17-9-28.
 */
class StoryRemoteSource(
        private val mServiceFactory : IServiceFactory,
        private val mCache: IStoryCache
) : IStorySource {

    override fun getLatestStories(): Flow<XResult<StoryListModel>> {

        return flowOfResult {

            val value = zhiHuService()
                    .getLatestStories()
                    .await()

            MapperFactory
                    .createStoryListMapper()
                    .transform(value)
                    .also { mCache.saveLatestStories(it) }
        }
    }

    override fun getStories(date: String): Flow<XResult<StoryListModel>> {

        return flowOfResult {

            val value = zhiHuService()
                    .getStories(date)
                    .await()

            MapperFactory
                    .createStoryListMapper()
                    .transform(value)
                    .also { mCache.saveStories(date, it) }
        }
    }

    override fun getStory(id: String): Flow<XResult<StoryDetailsModel>> {

        return flowOfResult {

            val value = zhiHuService()
                    .getStory(id)
                    .await()

            MapperFactory
                    .createStoryDetailsMapper()
                    .transform(value)
                    .also { mCache.saveStory(id, it) }
        }
    }

    private fun zhiHuService(): IStoryService =
            mServiceFactory.createService(IStoryService::class.java, Constant.Service.ZH_BASE_URL)
}