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

package com.sky.android.news.data.source

import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.HeadLineModel
import io.reactivex.Observable

/**
 * Created by sky on 17-9-21.
 */
class NewsDataRepository(sourceFactory: NewsSourceFactory) : NewsDataSource {

    private val mLocal = sourceFactory.createLocalSource()
    private val mRemote = sourceFactory.createRemoteSource()

    override fun getCategory(): Observable<CategoryModel> {

        val localObservable = mLocal.getCategory()
        val remoteObservable = mRemote.getCategory()

        return Observable
                .concat(localObservable, remoteObservable)
                .takeFirst { model -> model != null }
    }

    override fun getHeadLine(tid: String, start: Int, end: Int): Observable<HeadLineModel> {

        val localObservable = mLocal.getHeadLine(tid, start, end)
        val remoteObservable = mRemote.getHeadLine(tid, start, end)

        return Observable
                .concat(localObservable, remoteObservable)
                .takeFirst { model -> model != null }
    }

    override fun getDetails(docId: String): Observable<DetailsModel> {

        val localObservable = mLocal.getDetails(docId)
        val remoteObservable = mRemote.getDetails(docId)

        return Observable
                .concat(localObservable, remoteObservable)
                .takeFirst { model -> model != null }
    }
}