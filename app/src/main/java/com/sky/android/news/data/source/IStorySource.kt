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
interface IStorySource {

    /**
     * 获取最后一次列表
     */
    fun getLatestStories(): Observable<StoryListModel>

    /**
     * 获取指定日期的列表
     */
    fun getStories(date: String): Observable<StoryListModel>

    /**
     * 获取指定id的详情
     */
    fun getStory(id: String): Observable<StoryDetailsModel>
}