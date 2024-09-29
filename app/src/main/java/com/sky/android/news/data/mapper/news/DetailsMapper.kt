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

package com.sky.android.news.data.mapper.news

import com.sky.android.common.util.CollectionUtil
import com.sky.android.common.util.ConversionUtil
import com.sky.android.news.ext.toExtString
import com.sky.android.news.data.model.news.Content
import com.sky.android.news.data.model.news.ContentModel
import com.sky.android.news.data.model.news.Details
import com.sky.android.news.data.model.news.DetailsModel
import com.sky.android.news.data.model.news.Image
import com.sky.android.news.data.model.news.ImageModel
import com.sky.android.news.data.model.news.Relative
import com.sky.android.news.data.model.news.RelativeModel
import com.sky.android.news.data.model.news.SpInfo
import com.sky.android.news.data.model.news.SpInfoModel
import com.sky.android.news.data.model.news.Video
import com.sky.android.news.data.model.news.VideoModel
import javax.inject.Inject

/**
 * Created by sky on 17-9-22.
 */
class DetailsMapper @Inject constructor() {

    fun transform(details: Details): DetailsModel = DetailsModel(transform(details.content))

    private fun transform(content: Content): ContentModel {

        val image = transformImage(if (CollectionUtil.isEmpty(content.img)) listOf() else content.img)
        val video = transformVideo(if (CollectionUtil.isEmpty(content.video)) listOf() else content.video)
        val spInfo = transformSpInfo(if (CollectionUtil.isEmpty(content.spinfo)) listOf() else content.spinfo)
        val relative = transformRelative(if (CollectionUtil.isEmpty(content.relative_sys)) listOf() else content.relative_sys)

        return ContentModel(ConversionUtil.toString(content.template), image, ConversionUtil.toString(content.shareLink),
                ConversionUtil.toString(content.source), content.threadVote, ConversionUtil.toString(content.title),
                ConversionUtil.toString(content.body), ConversionUtil.toString(content.tid), content.picnews,
                spInfo, relative, ConversionUtil.toString(content.articleType), ConversionUtil.toString(content.digest),
                ConversionUtil.toString(content.ptime), ConversionUtil.toString(content.ec), ConversionUtil.toString(content.docid),
                content.threadAgainst, ConversionUtil.toString(content.hasNext), ConversionUtil.toString(content.dkeys),
                content.replyCount, ConversionUtil.toString(content.voicecomment), ConversionUtil.toString(content.replyBoard),
                ConversionUtil.toString(content.category), video)
    }

    private fun transformImage(image: List<Image>): List<ImageModel> =
            image.map { transformImage(it) }

    private fun transformImage(image: Image): ImageModel {
        return ImageModel(ConversionUtil.toString(image.ref),
                ConversionUtil.toString(image.src),
                ConversionUtil.toString(image.alt),
                ConversionUtil.toString(image.pixel))
    }

    private fun transformVideo(image: List<Video>): List<VideoModel> =
            image.map { transformVideo(it) }

    private fun transformVideo(image: Video): VideoModel {
        return VideoModel(ConversionUtil.toString(image.broadcast), ConversionUtil.toString(image.sizeHD),
                ConversionUtil.toString(image.url_mp4), ConversionUtil.toString(image.alt),
                image.length.toExtString(), image.videosource.toExtString(), image.appurl.toExtString(),
                image.m3u8Hd_url.toExtString(), image.mp4_url.toExtString(), image.sizeSD.toExtString(),
                image.sid.toExtString(), image.cover.toExtString(), image.vid.toExtString(),
                image.url_m3u8.toExtString(), image.sizeSHD.toExtString(), image.ref.toExtString(),
                image.topicid.toExtString(), image.commentboard.toExtString(),
                image.size.toExtString(), image.commentid.toExtString(), image.m3u8_url.toExtString())
    }

    private fun transformSpInfo(spInfo: List<SpInfo>): List<SpInfoModel> =
            spInfo.map { transformSpInfo(it) }

    private fun transformSpInfo(spInfo: SpInfo): SpInfoModel {
        return SpInfoModel(ConversionUtil.toString(spInfo.ref),
                ConversionUtil.toString(spInfo.spcontent),
                ConversionUtil.toString(spInfo.sptype))
    }

    private fun transformRelative(relatives: List<Relative>): List<RelativeModel> =
            relatives.map { transformRelative(it) }

    private fun transformRelative(relative: Relative): RelativeModel {
        return RelativeModel(ConversionUtil.toString(relative.docID),
                ConversionUtil.toString(relative.from),
                ConversionUtil.toString(relative.href),
                ConversionUtil.toString(relative.id),
                ConversionUtil.toString(relative.imgsrc),
                ConversionUtil.toString(relative.title),
                ConversionUtil.toString(relative.ptime))
    }
}