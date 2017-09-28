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

package com.sky.android.news.data.source.cloud

import com.sky.android.news.Constant
import com.sky.android.news.data.cache.NewsCache
import com.sky.android.news.data.mapper.MapperFactory
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.service.NewsService
import com.sky.android.news.data.source.NewsDataSource
import rx.Observable

/**
 * Created by sky on 17-9-21.
 */
class CloudNewsDataSource(private val mCache: NewsCache) : CloudDataSource(), NewsDataSource {

    override fun getCategory(): Observable<CategoryModel> {
        // 服务器没有这个功能
        return Observable.unsafeCreate<CategoryModel> { subscriber ->
            handler(subscriber, null)
        }
    }

    override fun getHeadLine(tid: String, start: Int, end: Int): Observable<HeadLineModel> {

        val service = buildNewsService()

        return service.getHeadLine(tid, start, end).map { it ->
            val model = MapperFactory.createHeadLineMapper().transform(it)
            // 保存到本地缓存
            mCache.saveHeadLine(tid, start, end, model)
            model
        }
    }

    override fun getDetails(docId: String): Observable<DetailsModel> {

        val service = buildNewsService()

        return service.getDetails(docId).map { it ->
            val model = MapperFactory.createDetailsMapper().transform(it)
            // 保存到本地缓存
            mCache.saveDetails(docId, model)
            model
        }
    }

    private fun buildNewsService(): NewsService {
        return buildService(NewsService::class.java, Constant.Service.BASE_URL)
    }
}