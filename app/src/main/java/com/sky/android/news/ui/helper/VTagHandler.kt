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

package com.sky.android.news.ui.helper

import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.view.View
import org.xml.sax.XMLReader
import java.util.*


/**
 * Created by sky on 17-9-27.
 *
 * 这个类处理的有问题，点击事件无法响应
 */
class VTagHandler : Html.TagHandler {

    override fun handleTag(opening: Boolean, tag: String, output: Editable, xmlReader: XMLReader) {

        if (tag.toLowerCase(Locale.getDefault()) == "img") {

            val len = output.length
            val images = output.getSpans(len - 1, len, ImageSpan::class.java)
            val imageUrl = images[0].source!!

            output.setSpan(ImageClick(imageUrl), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private inner class ImageClick(val imageUrl: String) : ClickableSpan() {

        override fun onClick(widget: View) {

            println(">>>>>>>>>>>>>>>>>>>> $imageUrl")
        }
    }
}