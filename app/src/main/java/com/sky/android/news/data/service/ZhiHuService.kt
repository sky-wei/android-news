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

package com.sky.android.news.data.service

import com.sky.android.news.data.zhihu.StoryDetails
import com.sky.android.news.data.zhihu.StoryList
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by sky on 17-9-28.
 */
interface ZhiHuService {

    @GET("api/4/stories/latest")
    fun getLatestStories(): Observable<StoryList>

    @GET("api/4/stories/before/{date}")
    fun getStories(@Path("date") date: String): Observable<StoryList>

    @GET("api/4/story/{id}")
    fun getStory(@Path("id") id: String): Observable<StoryDetails>
}