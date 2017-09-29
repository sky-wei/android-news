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
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.bumptech.glide.Glide
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.data.model.TopStoryItemModel

/**
 * Created by sky on 17-9-28.
 */
class BannerHolderCreator : CBViewHolderCreator<BannerHolderCreator.BannerImageHolder> {

    override fun createHolder(): BannerImageHolder {
        return BannerImageHolder()
    }

    inner class BannerImageHolder : Holder<TopStoryItemModel> {

        private lateinit var ivImage: ImageView
        private lateinit var tvTitle: TextView

        override fun createView(context: Context): View {

            val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_banner, null, false)

            ivImage = view.findViewById(R2.id.iv_image) as ImageView
            tvTitle = view.findViewById(R2.id.tv_title) as TextView

            return  view
        }

        override fun UpdateUI(context: Context, position: Int, data: TopStoryItemModel) {

            Glide.with(context)
                    .load(data.image)
                    .into(ivImage)
            tvTitle.text = data.title
        }
    }
}