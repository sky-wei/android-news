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
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.sky.android.common.utils.Alog
import java.lang.ref.WeakReference


/**
 * Created by sky on 17-9-24.
 */
class VImageGetter(val context: Context, private val textView: TextView) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable {

        Alog.d("loadImageSource: $source")

        val urlDrawable = UrlDrawable()

        // 加载图片
        Glide.with(context.applicationContext)
                .asDrawable()
                .load(source)
                .into(DrawableTarget(urlDrawable, textView))

        return urlDrawable
    }

    inner class DrawableTarget(urlDrawable: UrlDrawable, container: TextView) : SimpleTarget<Drawable>() {

        private val drawableReference: WeakReference<UrlDrawable> = WeakReference(urlDrawable)
        private val containerReference: WeakReference<TextView> = WeakReference(container)

        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {

            val height = resource.intrinsicHeight
            val width = resource.intrinsicWidth
            val scale = getScale(resource)

            val rect = Rect(0, 0, (width * scale).toInt(), (height * scale).toInt())

            resource.bounds = rect
            drawableReference.get()!!.bounds = rect
            drawableReference.get()!!.drawable = resource

            containerReference.get()!!.text = containerReference.get()!!.text
            containerReference.get()!!.invalidate()
        }

        private fun getScale(drawable: Drawable): Float {
            val maxWidth = containerReference.get()!!.width.toFloat()
            val originalDrawableWidth = drawable.intrinsicWidth.toFloat()
            return maxWidth / originalDrawableWidth
        }
    }

    inner class UrlDrawable : BitmapDrawable() {

        var drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            if (drawable != null)
                drawable!!.draw(canvas)
        }
    }
}