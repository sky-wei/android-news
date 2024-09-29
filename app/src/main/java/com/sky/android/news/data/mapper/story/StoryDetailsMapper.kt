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

package com.sky.android.news.data.mapper.story

import com.sky.android.common.util.CollectionUtil
import com.sky.android.news.ext.toExtString
import com.sky.android.news.data.model.story.SectionModel
import com.sky.android.news.data.model.story.StoryDetailsModel
import com.sky.android.news.data.model.story.Section
import com.sky.android.news.data.model.story.StoryDetails
import javax.inject.Inject

/**
 * Created by sky on 17-9-28.
 */
class StoryDetailsMapper @Inject constructor() {

    fun transform(storyDetails: StoryDetails): StoryDetailsModel {

        val js = if (CollectionUtil.isEmpty(storyDetails.js)) listOf("") else storyDetails.js
        val images = if (CollectionUtil.isEmpty(storyDetails.images)) listOf("") else storyDetails.images
        val css = if (CollectionUtil.isEmpty(storyDetails.css)) listOf("") else storyDetails.css

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