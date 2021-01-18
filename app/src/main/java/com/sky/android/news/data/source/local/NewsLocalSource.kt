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

package com.sky.android.news.data.source.local

import com.sky.android.news.data.cache.INewsCache
import com.sky.android.news.data.model.*
import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.ext.flowOfResult
import com.sky.android.news.ext.flowOfResultNull
import kotlinx.coroutines.flow.Flow

/**
 * Created by sky on 17-9-21.
 */
class NewsLocalSource(
        private val cache: INewsCache
) : INewsSource {

    override fun getCategory(): Flow<XResult<CategoryModel>> = flowOfResult {

        val itemModes = listOf(
                CategoryItemModel("头条", "T1348647909107"),
                CategoryItemModel("科技", "T1348649580692"),
                CategoryItemModel("历史", "T1368497029546"),
                CategoryItemModel("军事", "T1348648141035"),
                CategoryItemModel("要闻", "T1467284926140"),
                CategoryItemModel("手机", "T1348649654285"),
                CategoryItemModel("数码", "T1348649776727"),
                CategoryItemModel("智能", "T1351233117091"),
                CategoryItemModel("社会", "T1348648037603"))

        CategoryModel(itemModes)
    }

    override fun getHeadLine(tid: String, start: Int, end: Int): Flow<XResult<HeadLineModel>> =
            flowOfResultNull { cache.getHeadLine(tid, start, end) }

    override fun getDetails(docId: String): Flow<XResult<DetailsModel>> =
            flowOfResultNull { cache.getDetails(docId) }
}