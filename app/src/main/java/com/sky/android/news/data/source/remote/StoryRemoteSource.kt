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
import com.sky.android.news.data.cache.IStoryCache
import com.sky.android.news.data.mapper.MapperFactory
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.model.StoryListModel
import com.sky.android.news.data.service.IServiceFactory
import com.sky.android.news.data.service.IStoryService
import com.sky.android.news.data.source.IStorySource
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

/**
 * Created by sky on 17-9-28.
 */
class StoryRemoteSource(
        private val mServiceFactory : IServiceFactory,
        private val mCache: IStoryCache
) : IStorySource {

    override fun getLatestStories(): Observable<StoryListModel> {

        val service = buildZhiHuService()

        return service.getLatestStories().map { it ->
            val model = MapperFactory.createStoryListMapper().transform(it)
            // 保存到本地缓存
            mCache.saveLatestStories(model)
            model
        }
    }

    override fun getStories(date: String): Observable<StoryListModel> {

        val service = buildZhiHuService()

        return service.getStories(date).map { it ->
            val model = MapperFactory.createStoryListMapper().transform(it)
            // 保存到本地缓存
            mCache.saveStories(date, model)
            model
        }
    }

    override fun getStory(id: String): Observable<StoryDetailsModel> {

        val service = buildZhiHuService()

        return service.getStory(id).map { it ->
            val model = MapperFactory.createStoryDetailsMapper().transform(it)
            // 保存到本地缓存
            mCache.saveStory(id, model)
            model
        }
    }

    private fun buildZhiHuService(): IStoryService =
            mServiceFactory.createService(IStoryService::class.java, Constant.Service.ZH_BASE_URL)

    private fun <T> handler(observableEmitter: ObservableEmitter<in T>, model: T?) {

        try {
            if (model != null) {
                // 处理下一步
                observableEmitter.onNext(model)
            }

            // 完成
            observableEmitter.onComplete()
        } catch (e: Throwable) {
            // 出错了
            observableEmitter.onError(e)
        }
    }
}