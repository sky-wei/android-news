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

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sky.android.common.util.CollectionUtil
import com.sky.android.news.data.model.ContentModel
import com.sky.android.news.data.model.ImageModel
import com.sky.android.news.data.model.VideoModel
import io.reactivex.Observable


/**
 * Created by sky on 17-9-23.
 */
class DetailsHelper(val context: Context) {

    fun replaceImage(value: String, images: List<ImageModel>): String {

        if (CollectionUtil.isEmpty(images)) return value

        var tempValue = value

        images.forEach {
            // 替换图片
            tempValue = tempValue.replace(it.ref, getImageTag(it.src))
        }

        return tempValue
    }

    fun replaceVideo(value: String, images: List<VideoModel>): String {

        if (CollectionUtil.isEmpty(images)) return value

        var tempValue = value

        images.forEach {
            // 替换图片
            tempValue = tempValue.replace(it.ref, getImageTag(it.cover))
        }

        return tempValue
    }

    private fun getImageTag(url: String): String {
        return "<img src=\"$url\" />"
    }

    fun test(textView: TextView, content: ContentModel): Observable<Spanned> {

        return Observable.unsafeCreate<Spanned> {

            try {
                val body = replaceImage(content.body, content.img)
                it.onNext(Html.fromHtml(body, Test(textView), null))
            } catch (tr: Throwable) {
                it.onError(tr)
            }
        }
    }

    inner class Test(private val textView: TextView) : Html.ImageGetter {

        override fun getDrawable(source: String): Drawable {

            val drawable = Glide.with(context).asDrawable().load(source).submit().get()
            val scale = getScale(drawable)
            drawable.setBounds(0, 0, (drawable.intrinsicWidth * scale).toInt(), (drawable.intrinsicHeight * scale).toInt())

            return drawable
        }

        private fun getScale(drawable: Drawable): Float {
            val maxWidth = textView.width.toFloat()
            val originalDrawableWidth = drawable.intrinsicWidth.toFloat()
            return maxWidth / originalDrawableWidth
        }
    }
}