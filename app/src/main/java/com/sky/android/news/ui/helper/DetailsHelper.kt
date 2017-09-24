package com.sky.android.news.ui.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sky.android.common.utils.CollectionUtils
import com.sky.android.news.data.model.ContentModel
import com.sky.android.news.data.model.ImageModel
import com.sky.android.news.data.model.VideoModel
import rx.Observable


/**
 * Created by sky on 17-9-23.
 */
class DetailsHelper(val context: Context) {

    fun replaceImage(value: String, images: List<ImageModel>): String {

        if (CollectionUtils.isEmpty(images)) return value

        var tempValue = value

        images.forEach {
            // 替换图片
            tempValue = tempValue.replace(it.ref, getImageTag(it.src))
        }

        return tempValue
    }

    fun replaceVideo(value: String, images: List<VideoModel>): String {

        if (CollectionUtils.isEmpty(images)) return value

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