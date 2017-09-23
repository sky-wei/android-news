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
import com.sky.android.common.utils.ConversionUtils
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

        val image = transformImage(if (CollectionUtils.isEmpty(content.img)) listOf() else content.img)
        val spInfo = transformSpInfo(if (CollectionUtils.isEmpty(content.spinfo)) listOf() else content.spinfo)
        val relative = transformRelative(if (CollectionUtils.isEmpty(content.relative_sys)) listOf() else content.relative_sys)

        return ContentModel(ConversionUtils.toString(content.template), image, ConversionUtils.toString(content.shareLink),
                ConversionUtils.toString(content.source), content.threadVote, ConversionUtils.toString(content.title),
                ConversionUtils.toString(content.body), ConversionUtils.toString(content.tid), content.picnews,
                spInfo, relative, ConversionUtils.toString(content.articleType), ConversionUtils.toString(content.digest),
                ConversionUtils.toString(content.ptime), ConversionUtils.toString(content.ec), ConversionUtils.toString(content.docid),
                content.threadAgainst, ConversionUtils.toString(content.hasNext), ConversionUtils.toString(content.dkeys),
                content.replyCount, ConversionUtils.toString(content.voicecomment), ConversionUtils.toString(content.replyBoard),
                ConversionUtils.toString(content.category))
    }

    private fun transformImage(image: List<Image>): List<ImageModel> {
        return image.map { it -> transformImage(it) }
    }

    private fun transformImage(image: Image): ImageModel {
        return ImageModel(ConversionUtils.toString(image.ref),
                ConversionUtils.toString(image.src),
                ConversionUtils.toString(image.alt),
                ConversionUtils.toString(image.pixel))
    }

    private fun transformSpInfo(spInfo: List<SpInfo>): List<SpInfoModel> {
        return spInfo.map { it -> transformSpInfo(it) }
    }

    private fun transformSpInfo(spInfo: SpInfo): SpInfoModel {
        return SpInfoModel(ConversionUtils.toString(spInfo.ref),
                ConversionUtils.toString(spInfo.spcontent),
                ConversionUtils.toString(spInfo.sptype))
    }

    private fun transformRelative(relatives: List<Relative>): List<RelativeModel> {
        return relatives.map { it -> transformRelative(it) }
    }

    private fun transformRelative(relative: Relative): RelativeModel {
        return RelativeModel(ConversionUtils.toString(relative.docID),
                ConversionUtils.toString(relative.from),
                ConversionUtils.toString(relative.href),
                ConversionUtils.toString(relative.id),
                ConversionUtils.toString(relative.imgsrc),
                ConversionUtils.toString(relative.title),
                ConversionUtils.toString(relative.ptime))
    }
}