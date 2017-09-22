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

import com.sky.android.news.data.model.*
import com.sky.android.news.data.news.*

/**
 * Created by sky on 17-9-22.
 */
class DetailsMapper {

    fun transform(details: Details): DetailsModel {
        return DetailsModel(transform(details.content))
    }

    private fun transform(content: Content): ContentModel {

        val image = transformImage(content.img)
        val spInfo = transformSpInfo(content.spinfo)
        val relative = transformRelative(content.relative_sys)

        return ContentModel(content.template, image, content.shareLink, content.source,
                content.threadVote, content.title, content.body, content.tid, content.picnews,
                spInfo, relative, content.articleType, content.digest, content.ptime,
                content.ec, content.docid, content.threadAgainst, content.hasNext,
                content.dkeys, content.replyCount, content.voicecomment, content.replyBoard,
                content.category)
    }

    private fun transformImage(image: List<Image>): List<ImageModel> {
        return image.map { it -> transformImage(it) }
    }

    private fun transformImage(image: Image): ImageModel {
        return ImageModel(image.ref, image.src, image.alt, image.pixel)
    }

    private fun transformSpInfo(spInfo: List<SpInfo>): List<SpInfoModel> {
        return spInfo.map { it -> transformSpInfo(it) }
    }

    private fun transformSpInfo(spInfo: SpInfo): SpInfoModel {
        return SpInfoModel(spInfo.ref, spInfo.spcontent, spInfo.sptype)
    }

    private fun transformRelative(relatives: List<Relative>): List<RelativeModel> {
        return relatives.map { it -> transformRelative(it) }
    }

    private fun transformRelative(relative: Relative): RelativeModel {
        return RelativeModel(relative.docID, relative.from, relative.href,
                relative.id, relative.imgsrc, relative.title, relative.ptime)
    }
}