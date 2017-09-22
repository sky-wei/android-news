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
 * Created by sky on 17-9-22.
 */
data class DetailsModel(val models: ContentModel) : Serializable

data class ContentModel(val template: String, val img: List<ImageModel>, val shareLink: String,
                        val source: String, val threadVote: Int, val title: String,
                        val body: String, val tid: String, val picNews: Boolean, val spInfo: List<SpInfoModel>,
                        val relative: List<RelativeModel>, val articleType: String, val digest: String,
                        val pTime: String, val ec: String, val docId: String, val threadAgainst: Int,
                        val hasNext: String, val dKeys: String, val replyCount: Int, val voiceComment: String,
                        val replyBoard: String, val category: String)

data class ImageModel(val ref: String, val src: String, val alt: String, val pixel: String) : Serializable

data class SpInfoModel(val ref: String, val spContent: String, val spType: String) : Serializable

data class RelativeModel(val docID: String, val from: String, val href: String, val id: String,
                    val imgSrc: String, val title: String, val pTime: String) : Serializable