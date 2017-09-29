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

package com.sky.android.news.data.story

import java.io.Serializable

/**
 * Created by sky on 17-9-28.
 */
data class StoryList(val date: String, val stories: List<StoryItem>, val top_stories: List<TopStory>) : Serializable

data class StoryItem(val images: List<String>, val type: Int,
                   val id: Long, val ga_prefix: String, val title: String) : Serializable

data class TopStory(val image: String, val type: Int,
                      val id: Long, val ga_prefix: String, val title: String) : Serializable