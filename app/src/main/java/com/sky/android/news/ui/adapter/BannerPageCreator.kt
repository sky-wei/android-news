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

package com.sky.android.news.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.bumptech.glide.Glide
import com.sky.android.news.data.model.TopStoryModel

/**
 * Created by sky on 17-9-28.
 */
class BannerPageCreator : CBViewHolderCreator<BannerPageCreator.BannerImageHolder> {

    override fun createHolder(): BannerImageHolder {
        return BannerImageHolder()
    }

    inner class BannerImageHolder : Holder<TopStoryModel> {

        lateinit var mImageView: ImageView

        override fun createView(context: Context): View {
            mImageView = ImageView(context)
            mImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            return mImageView
        }

        override fun UpdateUI(context: Context, position: Int, data: TopStoryModel) {

            Glide.with(context)
                    .load(data.image)
                    .into(mImageView)
        }
    }
}