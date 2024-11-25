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

package com.sky.android.news.data.repository.news

import androidx.paging.PagingData
import com.sky.android.news.data.model.news.CategoryModel
import com.sky.android.news.data.model.news.DetailsModel
import com.sky.android.news.data.model.news.LineItemModel
import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.data.source.di.LocalSource
import com.sky.android.news.data.source.di.RemoteSource
import com.sky.android.news.ext.concatResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by sky on 17-9-21.
 */
class NewsRepository @Inject constructor(
    @LocalSource private val local: INewsSource,
    @RemoteSource private val remote: INewsSource
) : INewsRepository {

    override fun getCategory(): Flow<CategoryModel> {
        return local.getCategory().concatResult {
            remote.getCategory()
        }
    }

    override fun getHeadLine(tid: String): Flow<PagingData<LineItemModel>> =
        remote.getHeadLine(tid)

    override fun getDetails(docId: String): Flow<DetailsModel> {
        return local.getDetails(docId).concatResult {
            remote.getDetails(docId)
        }
    }
}