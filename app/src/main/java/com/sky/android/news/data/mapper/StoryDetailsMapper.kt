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

import com.sky.android.common.utils.CollectionUtils
import com.sky.android.news.base.toExtString
import com.sky.android.news.data.model.SectionModel
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.story.Section
import com.sky.android.news.data.story.StoryDetails

/**
 * Created by sky on 17-9-28.
 */
class StoryDetailsMapper {

    fun transform(storyDetails: StoryDetails): StoryDetailsModel {

        val js = if (CollectionUtils.isEmpty(storyDetails.js)) listOf("") else storyDetails.js
        val images = if (CollectionUtils.isEmpty(storyDetails.images)) listOf("") else storyDetails.images
        val css = if (CollectionUtils.isEmpty(storyDetails.css)) listOf("") else storyDetails.css

        return StoryDetailsModel(storyDetails.body.toExtString(), storyDetails.image_source.toExtString(),
                storyDetails.title.toExtString(), storyDetails.image.toExtString(), storyDetails.share_url.toExtString(),
                js, storyDetails.ga_prefix, transformSection(storyDetails.section), images, storyDetails.type,
                storyDetails.id, css)
    }

    private fun transformSection(section: Section?): SectionModel {

        if (section == null) return SectionModel()

        return SectionModel(section.thumbnail.toExtString(),
                section.id, section.name.toExtString())
    }
}