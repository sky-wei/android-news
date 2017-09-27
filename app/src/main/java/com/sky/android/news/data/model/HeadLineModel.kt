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

package com.sky.android.news.data.model

import java.io.Serializable

/**
 * Created by sky on 17-9-21.
 */
data class HeadLineModel(var lineItems: List<LineItemModel>) : Serializable

data class LineItemModel(val voteCount: Int, val docId: String, val lModify: String,
                         val url3w: String, val source: String, val posTid: String,
                         val priority: Int, val title: String, val replyCount: Int,
                         val lTitle: String, val subtitle: String, val digest: String,
                         val boardId: String, val imgSrc: String, val pTime: String,
                         val dayNum: String, val template: String /** 特殊的字段，用于区分不支持的新闻 */) : Serializable

data class LinePackageModel(val lastTime: Long, val model: HeadLineModel) : Serializable