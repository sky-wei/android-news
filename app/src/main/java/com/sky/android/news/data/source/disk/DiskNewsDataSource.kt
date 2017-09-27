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

package com.sky.android.news.data.source.disk

import android.content.Context
import com.sky.android.news.data.cache.NewsCache
import com.sky.android.news.data.model.CategoryItemModel
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.source.NewsDataSource
import rx.Observable
import rx.Subscriber

/**
 * Created by sky on 17-9-21.
 */
class DiskNewsDataSource(private val mContext: Context, private val mCache: NewsCache) : NewsDataSource {

    override fun getCategory(): Observable<CategoryModel> {
        return Observable.unsafeCreate { subscriber ->
            handler(subscriber, newCategory())
        }
    }

    override fun getHeadLine(tid: String, start: Int, end: Int): Observable<HeadLineModel> {
        return Observable.unsafeCreate { subscriber ->
            handler(subscriber, mCache.getHeadLine(tid, start, end))
        }
    }

    override fun getDetails(docId: String): Observable<DetailsModel> {
        return Observable.unsafeCreate { subscriber ->
            handler(subscriber, mCache.getDetails(docId))
        }
    }

    private fun <T> handler(subscriber: Subscriber<in T>, model: T?) {

        try {
            // 处理下一步
            subscriber.onNext(model)

            // 完成
            subscriber.onCompleted()
        } catch (e: Throwable) {
            // 出错了
            subscriber.onError(e)
        }
    }

    /**
     * 使用本地分类
     */
    private fun newCategory(): CategoryModel {

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

        return CategoryModel(itemModes)
    }
}