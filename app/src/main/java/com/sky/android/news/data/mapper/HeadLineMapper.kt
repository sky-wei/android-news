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

import com.sky.android.common.util.ConversionUtil
import com.sky.android.news.base.toExtString
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.news.HeadLine
import com.sky.android.news.data.news.LineItem
import javax.inject.Inject

/**
 * Created by sky on 17-9-22.
 */
class HeadLineMapper @Inject constructor() {

    fun transform(headLine: HeadLine): HeadLineModel =
            HeadLineModel(transform(headLine.lineItems))

    private fun transform(lineItems: List<LineItem>): List<LineItemModel> =
            lineItems.map { transform(it) }

    private fun transform(item: LineItem): LineItemModel {
        return LineItemModel(item.votecount, ConversionUtil.toString(item.docid),
                ConversionUtil.toString(item.lmodify), ConversionUtil.toString(item.url_3w),
                ConversionUtil.toString(item.source), ConversionUtil.toString(item.postid),
                item.priority, ConversionUtil.toString(item.title), item.replyCount,
                ConversionUtil.toString(item.ltitle), ConversionUtil.toString(item.subtitle),
                ConversionUtil.toString(item.digest), ConversionUtil.toString(item.boardid),
                ConversionUtil.toString(item.imgsrc), ConversionUtil.toString(item.ptime),
                ConversionUtil.toString(item.daynum), item.template.toExtString())
    }
}