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

package com.sky.android.news.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sky.android.news.data.cache.news.INewsCache
import com.sky.android.news.data.model.news.LineItemModel

/**
 * Created by sky on 11/24/24.
 */
class NewsPagingLocalSource(
    private val tid: String,
    private val cache: INewsCache
): PagingSource<Int, LineItemModel>() {

    override fun getRefreshKey(
        state: PagingState<Int, LineItemModel>
    ): Int? = state.anchorPosition

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, LineItemModel> {
        return try {
            val nextPage = params.key ?: 1
            val result = cache.getHeadLine(
                tid, nextPage * 10, nextPage * 20
            )
            LoadResult.Page(
                data = result?.lineItems ?: emptyList(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (result == null) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}