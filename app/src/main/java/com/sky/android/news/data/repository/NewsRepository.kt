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

import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.model.XResult
import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.di.LocalSource
import com.sky.android.news.di.RemoteSource
import com.sky.android.news.ext.concatResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by sky on 17-9-21.
 */
class NewsRepository @Inject constructor(
    @LocalSource private val local: INewsSource,
    @RemoteSource private val remote: INewsSource
) : INewsRepository {

    override fun getCategory(): Flow<XResult<CategoryModel>> =
            concatResult(local.getCategory()) { remote.getCategory() }
                    .flowOn(Dispatchers.IO)

    override fun getHeadLine(tid: String, start: Int, end: Int): Flow<XResult<HeadLineModel>> =
            concatResult(local.getHeadLine(tid, start, end)) { remote.getHeadLine(tid, start, end) }
                    .flowOn(Dispatchers.IO)

    override fun getDetails(docId: String): Flow<XResult<DetailsModel>> =
            concatResult(local.getDetails(docId)) { remote.getDetails(docId) }
                    .flowOn(Dispatchers.IO)
}