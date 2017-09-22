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

import com.sky.android.news.data.news.Details
import com.sky.android.news.data.news.HeadLine
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by sky on 17-9-21.
 */
interface NewsService {

//    fun getCategory(): Observable<Category>

    @GET("nc/article/headline/{tid}/{start}-{end}.html")
    fun getHeadLine(@Path("tid") tid: String,
                    @Path("start") start: Int, @Path("end") end: Int): Observable<HeadLine>

    @GET("nc/article/{docId}/full.html")
    fun getDetails(@Path("docId") docId: String): Observable<Details>
}