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

package com.sky.android.news.data.model.story

import java.io.Serializable

/**
 * Created by sky on 17-9-28.
 */
data class StoryListModel(val date: String, val stories: List<StoryItemModel>, val topStories: List<TopStoryItemModel>) : Serializable

data class TopStoryListModel(val topStories: List<TopStoryItemModel>, override val viewType: Int = 0) :
    BaseViewType

data class StoryItemModel(val images: List<String>, override val type: Int,
                          override val id: Long, val gaPrefix: String, val title: String, override val viewType: Int = 1) : BaseItemModel,
    BaseViewType

data class NodeItemModel(val data: String, val node: String = "", override val viewType: Int = 2) :
    BaseViewType

data class TopStoryItemModel(val image: String, override val type: Int,
                             override val id: Long, val gaPrefix: String, val title: String) : BaseItemModel, Serializable

interface BaseViewType : Serializable {
    val viewType: Int
}

interface BaseItemModel : Serializable {
    val id: Long
    val type: Int
}

data class StoryListPackageModel(val lastTime: Long, val model: StoryListModel) : Serializable