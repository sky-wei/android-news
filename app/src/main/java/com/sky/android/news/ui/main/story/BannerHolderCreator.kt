/*
 * Copyright (c) 2020 The sky Authors.
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

package com.sky.android.news.ui.main.story

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.bumptech.glide.Glide
import com.sky.android.news.data.model.TopStoryItemModel
import com.sky.android.news.databinding.ItemBannerBinding

/**
 * Created by sky on 17-9-28.
 */
class BannerHolderCreator : CBViewHolderCreator<BannerHolderCreator.BannerImageHolder> {

    override fun createHolder(): BannerImageHolder = BannerImageHolder()

    inner class BannerImageHolder : Holder<TopStoryItemModel> {

        private lateinit var binding: ItemBannerBinding

        override fun createView(context: Context): View {
            binding = ItemBannerBinding.inflate(
                    LayoutInflater.from(context), null, false
            )
            return  binding.root
        }

        override fun UpdateUI(context: Context, position: Int, data: TopStoryItemModel) {

            Glide.with(context)
                    .load(data.image)
                    .into(binding.ivImage)
            binding.tvTitle.text = data.title
        }
    }
}