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

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sky.android.news.data.cache.news.INewsCache
import com.sky.android.news.data.mapper.news.DetailsMapper
import com.sky.android.news.data.mapper.news.HeadLineMapper
import com.sky.android.news.data.model.news.CategoryModel
import com.sky.android.news.data.model.news.DetailsModel
import com.sky.android.news.data.model.news.LineItemModel
import com.sky.android.news.data.service.INewsService
import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.data.source.paging.NewsPagingRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * Created by sky on 17-9-21.
 */
class NewsRemoteSource @Inject constructor(
    private val newsService: INewsService,
    private val cache: INewsCache,
    private val headLineMapper: HeadLineMapper,
    private val detailsMapper: DetailsMapper
) : INewsSource {

    override fun getCategory(): Flow<CategoryModel> = flowOf(CategoryModel.EMPTY)

    override fun getHeadLine(tid: String): Flow<PagingData<LineItemModel>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingRemoteSource(
                    tid = tid,
                    newsService = newsService,
                    cache = cache,
                    headLineMapper = headLineMapper
                )
            }
        ).flow

    override fun getDetails(docId: String): Flow<DetailsModel> = flow {

        val result = newsService.getDetails(docId)

        emit(
            detailsMapper
                .transform(result)
                .also { cache.saveDetails(docId, it) }
        )
    }
}