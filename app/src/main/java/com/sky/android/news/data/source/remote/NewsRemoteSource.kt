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
import com.sky.android.news.data.cache.INewsCache
import com.sky.android.news.data.mapper.MapperFactory
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.model.XResult
import com.sky.android.news.data.service.INewsService
import com.sky.android.news.data.service.IServiceFactory
import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.ext.asFlow
import com.sky.android.news.ext.flowOfResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by sky on 17-9-21.
 */
class NewsRemoteSource(
        private val mServiceFactory : IServiceFactory,
        private val mCache: INewsCache
) : INewsSource {

    override fun getCategory(): Flow<XResult<CategoryModel>> = XResult.Invalid.asFlow()

    override fun getHeadLine(tid: String, start: Int, end: Int): Flow<XResult<HeadLineModel>> {
        return flowOfResult {

            val value = newsService()
                    .getHeadLine(tid, start, end)
                    .await()

            MapperFactory
                    .createHeadLineMapper()
                    .transform(value)
                    .also { mCache.saveHeadLine(tid, start, end, it) }
        }
    }

    override fun getDetails(docId: String): Flow<XResult<DetailsModel>> {
        return flowOfResult {

            val value = newsService()
                    .getDetails(docId)
                    .await()

            MapperFactory
                    .createDetailsMapper()
                    .transform(value)
                    .also { mCache.saveDetails(docId, it) }
        }
    }

    private fun newsService(): INewsService =
            mServiceFactory.createService(INewsService::class.java, Constant.Service.BASE_URL)
}