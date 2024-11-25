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

import com.sky.android.news.data.cache.story.IStoryCache
import com.sky.android.news.data.mapper.story.StoryDetailsMapper
import com.sky.android.news.data.mapper.story.StoryListMapper
import com.sky.android.news.data.model.story.StoryDetailsModel
import com.sky.android.news.data.model.story.StoryListModel
import com.sky.android.news.data.service.IStoryService
import com.sky.android.news.data.source.IStorySource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by sky on 17-9-28.
 */
class StoryRemoteSource @Inject constructor(
    private val mStoryService: IStoryService,
    private val mCache: IStoryCache,
    private val mStoryListMapper: StoryListMapper,
    private val mStoryDetailsMapper: StoryDetailsMapper,
) : IStorySource {

    override fun getLatestStories(): Flow<StoryListModel> = flow {

        val result = mStoryService.getLatestStories()

        emit(
            mStoryListMapper
                .transform(result)
                .also { mCache.saveLatestStories(it) }
        )
    }

    override fun getStories(date: String): Flow<StoryListModel> = flow {

        val result = mStoryService.getStories(date)

        emit(
            mStoryListMapper
                .transform(result)
                .also { mCache.saveStories(date, it) }
        )
    }

    override fun getStory(id: String): Flow<StoryDetailsModel> = flow {

        val result = mStoryService.getStory(id)

        emit(
            mStoryDetailsMapper
                .transform(result)
                .also { mCache.saveStory(id, it) }
        )
    }
}