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

package com.sky.android.news.data.zhihu

import java.io.Serializable

/**
 * Created by sky on 17-9-28.
 */
data class StoryDetails(val body: String, val image_source: String, val title: String,
                 val image: String, val share_url: String, val js: List<String>,
                 val ga_prefix: String, val section: Section, val images: List<String>,
                 val type: Int, val id: Long, val css: List<String>) : Serializable

data class Section(val thumbnail: String, val id: Long, val name: String) : Serializable