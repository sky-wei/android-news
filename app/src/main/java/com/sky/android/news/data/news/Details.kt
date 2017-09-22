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

package com.sky.android.news.data.news

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by sky on 17-9-22.
 */
data class Details(@SerializedName("CUU3HCLM0001899N") val content: Content) : Serializable

data class Content(val template: String, val img: List<Image>, val shareLink: String,
                   val source: String, val threadVote: Int, val title: String,
                   val body: String, val tid: String, val picnews: Boolean, val spinfo: List<SpInfo>,
                   val relative_sys: List<Relative>, val articleType: String, val digest: String,
                   val ptime: String, val ec: String, val docid: String, val threadAgainst: Int,
                   val hasNext: String, val dkeys: String, val replyCount: Int, val voicecomment: String,
                   val replyBoard: String, val category: String) : Serializable

data class Image(val ref: String, val src: String, val alt: String, val pixel: String) : Serializable

data class SpInfo(val ref: String, val spcontent: String, val sptype: String) : Serializable

data class Relative(val docID: String, val from: String, val href: String, val id: String,
                    val imgsrc: String, val title: String, val ptime: String) : Serializable