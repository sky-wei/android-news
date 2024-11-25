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

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sky.android.news.data.cache.news.INewsCache
import com.sky.android.news.data.model.news.CategoryItemModel
import com.sky.android.news.data.model.news.CategoryModel
import com.sky.android.news.data.model.news.DetailsModel
import com.sky.android.news.data.model.news.LineItemModel
import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.data.source.paging.NewsPagingLocalSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by sky on 17-9-21.
 */
class NewsLocalSource @Inject constructor(
    private val cache: INewsCache
) : INewsSource {

    override fun getCategory(): Flow<CategoryModel> = flow {

        val itemModes = listOf(
            CategoryItemModel("头条", "T1348647909107"),
            CategoryItemModel("科技", "T1348649580692"),
            CategoryItemModel("历史", "T1368497029546"),
            CategoryItemModel("军事", "T1348648141035"),
            CategoryItemModel("要闻", "T1467284926140"),
            CategoryItemModel("手机", "T1348649654285"),
            CategoryItemModel("数码", "T1348649776727")
        )

        emit(CategoryModel(itemModes))
    }

    override fun getHeadLine(
        tid: String
    ): Flow<PagingData<LineItemModel>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingLocalSource(
                    tid = tid,
                    cache = cache
                )
            }
        ).flow

    override fun getDetails(docId: String): Flow<DetailsModel> = flow {
        emit(cache.getDetails(docId) ?: DetailsModel.EMPTY)
    }
}