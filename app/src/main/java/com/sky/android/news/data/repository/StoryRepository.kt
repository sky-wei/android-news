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

package com.sky.android.news.data.repository

import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.model.StoryListModel
import com.sky.android.news.data.model.XResult
import com.sky.android.news.data.source.IStorySource
import com.sky.android.news.di.LocalSource
import com.sky.android.news.di.RemoteSource
import com.sky.android.news.ext.concatResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by sky on 17-9-28.
 */
class StoryRepository @Inject constructor(
    @LocalSource private val local: IStorySource,
    @RemoteSource private val remote: IStorySource
) : IStoryRepository {

    override fun getLatestStories(): Flow<XResult<StoryListModel>> =
            concatResult(local.getLatestStories()) { remote.getLatestStories() }
                    .flowOn(Dispatchers.IO)

    override fun getStories(date: String): Flow<XResult<StoryListModel>> =
            concatResult(local.getStories(date)) { remote.getStories(date) }
                    .flowOn(Dispatchers.IO)

    override fun getStory(id: String): Flow<XResult<StoryDetailsModel>> =
            concatResult(local.getStory(id)) { remote.getStory(id) }
                    .flowOn(Dispatchers.IO)
}