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
 * Created by sky on 17-9-28.
 */
data class StoryDetailsModel(val body: String, val imageSource: String, val title: String,
                             val image: String, val shareUrl: String, val js: List<String>,
                             val gaPrefix: String, val section: SectionModel, val images: List<String>,
                             val type: Int, val id: Long, val css: List<String>) : Serializable

data class SectionModel(val thumbnail: String = "", val id: Long = 0, val name: String = "") : Serializable

data class StoryDetailsPackageModel(val lastTime: Long, val model: StoryDetailsModel) : Serializable