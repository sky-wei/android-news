/*
 * Copyright (c) 2024 The sky Authors.
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

package com.sky.android.news.data.model.news

import java.io.Serializable

/**
 * Created by sky on 17-9-21.
 */

data class HeadLine(var lineItems: List<LineItem>) : Serializable

data class LineItem(val votecount: Int, val docid: String, val lmodify: String,
                    val url_3w: String, val source: String, val postid: String,
                    val priority: Int, val title: String, val replyCount: Int,
                    val ltitle: String, val subtitle: String, val digest: String,
                    val boardid: String, val imgsrc: String, val ptime: String,
                    val daynum: String, val template: String /** 特殊的字段，用于区分不支持的新闻 */) : Serializable
