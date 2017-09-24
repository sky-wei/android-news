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

import com.sky.android.common.utils.ConversionUtils
import com.sky.android.news.base.toExtString
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.news.HeadLine
import com.sky.android.news.data.news.LineItem

/**
 * Created by sky on 17-9-22.
 */
class HeadLineMapper {

    fun transform(headLine: HeadLine): HeadLineModel {
        return HeadLineModel(transform(headLine.lineItems))
    }

    private fun transform(lineItems: List<LineItem>): List<LineItemModel> {
        return lineItems.map { it -> transform(it) }
    }

    private fun transform(item: LineItem): LineItemModel {
        return LineItemModel(item.votecount, ConversionUtils.toString(item.docid),
                ConversionUtils.toString(item.lmodify), ConversionUtils.toString(item.url_3w),
                ConversionUtils.toString(item.source), ConversionUtils.toString(item.postid),
                item.priority, ConversionUtils.toString(item.title), item.replyCount,
                ConversionUtils.toString(item.ltitle), ConversionUtils.toString(item.subtitle),
                ConversionUtils.toString(item.digest), ConversionUtils.toString(item.boardid),
                ConversionUtils.toString(item.imgsrc), ConversionUtils.toString(item.ptime),
                ConversionUtils.toString(item.daynum), item.template.toExtString())
    }
}