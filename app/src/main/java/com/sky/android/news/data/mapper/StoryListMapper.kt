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

package com.sky.android.news.data.mapper

import com.sky.android.common.util.CollectionUtil
import com.sky.android.news.base.toExtString
import com.sky.android.news.data.model.StoryItemModel
import com.sky.android.news.data.model.StoryListModel
import com.sky.android.news.data.model.TopStoryItemModel
import com.sky.android.news.data.story.StoryItem
import com.sky.android.news.data.story.StoryList
import com.sky.android.news.data.story.TopStory

/**
 * Created by sky on 17-9-28.
 */
class StoryListMapper {

    fun transform(storyList: StoryList): StoryListModel {

        val storyItem = transformStoryItem(storyList.stories)
        val topStory = transformTopStory(storyList.top_stories)

        return StoryListModel(storyList.date.toExtString(), storyItem, topStory)
    }

    private fun transformStoryItem(storyItem: List<StoryItem>?): List<StoryItemModel> {

        if (CollectionUtil.isEmpty(storyItem)) return listOf()

        return storyItem!!.map { transformStoryItem(it) }
    }

    private fun transformStoryItem(storyItem: StoryItem): StoryItemModel {

        return StoryItemModel(
                if (CollectionUtil.isEmpty(storyItem.images)) listOf() else storyItem.images,
                storyItem.type, storyItem.id, storyItem.ga_prefix.toExtString(), storyItem.title.toExtString())
    }

    private fun transformTopStory(topStory: List<TopStory>?): List<TopStoryItemModel> {

        if (CollectionUtil.isEmpty(topStory)) return listOf()

        return topStory!!.map { transformTopStory(it) }
    }

    private fun transformTopStory(topStory: TopStory): TopStoryItemModel {

        return TopStoryItemModel(topStory.image.toExtString(), topStory.type,
                topStory.id, topStory.ga_prefix.toExtString(), topStory.title.toExtString())
    }
}