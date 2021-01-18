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

import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.model.StoryListModel
import io.reactivex.Observable

/**
 * Created by sky on 17-9-28.
 */
class StoryRepository(
        private val local: IStorySource,
        private val remote: IStorySource
) : IStorySource {

    override fun getLatestStories(): Observable<StoryListModel> {

        val localObservable = local.getLatestStories()
        val remoteObservable = remote.getLatestStories()

        return Observable
                .concat(localObservable, remoteObservable)
    }

    override fun getStories(date: String): Observable<StoryListModel> {

        val localObservable = local.getStories(date)
        val remoteObservable = remote.getStories(date)

        return Observable
                .concat(localObservable, remoteObservable)
    }

    override fun getStory(id: String): Observable<StoryDetailsModel> {

        val localObservable = local.getStory(id)
        val remoteObservable = remote.getStory(id)

        return Observable
                .concat(localObservable, remoteObservable)
    }
}