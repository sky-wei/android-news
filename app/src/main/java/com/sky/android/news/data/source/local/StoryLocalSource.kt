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

import com.sky.android.news.data.cache.IStoryCache
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.model.StoryListModel
import com.sky.android.news.data.source.IStorySource
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

/**
 * Created by sky on 17-9-28.
 */
class StoryLocalSource(
        private val cache: IStoryCache
) : IStorySource {

    override fun getLatestStories(): Observable<StoryListModel> =
            Observable.create { handler(it, cache.getLatestStories()) }

    override fun getStories(date: String): Observable<StoryListModel> =
            Observable.create { handler(it, cache.getStories(date)) }

    override fun getStory(id: String): Observable<StoryDetailsModel> =
            Observable.create { handler(it, cache.getStory(id)) }

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