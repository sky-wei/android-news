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
import com.sky.android.news.data.model.XResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by sky on 17-9-21.
 */

interface INewsSource {

    /**
     * 获取分类列表
     */
    fun getCategory(): Flow<XResult<CategoryModel>>

    /**
     * 获取新闻列表
     */
    fun getHeadLine(
        tid: String, start: Int, end: Int
    ): Flow<XResult<HeadLineModel>>

    /**
     * 获取详情信息
     */
    fun getDetails(docId: String): Flow<XResult<DetailsModel>>
}
