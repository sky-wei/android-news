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
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.sky.android.common.adapter.SimpleRecyclerAdapter
import com.sky.android.common.base.BaseRecyclerAdapter
import com.sky.android.common.base.BaseRecyclerHolder
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.data.model.ItemModel
import com.sky.android.news.data.model.StoryItemModel

/**
 * Created by sky on 17-9-28.
 */
class StoryAdapter(context: Context) : SimpleRecyclerAdapter<ItemModel>(context) {

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup, viewType: Int): View {
        return layoutInflater.inflate(R.layout.item_story_list, viewGroup, false)
    }

    override fun onCreateViewHolder(view: View, viewType: Int): BaseRecyclerHolder<ItemModel> {
        return StoryHolder(view, this)
    }

    inner class StoryHolder(itemView: View, adapter: BaseRecyclerAdapter<ItemModel>)
        : BaseRecyclerHolder<ItemModel>(itemView, adapter) {

        @BindView(R2.id.iv_image)
        lateinit var ivImage: ImageView
        @BindView(R2.id.tv_title)
        lateinit var tvTitle: TextView
        @BindView(R2.id.tv_source)
        lateinit var tvSource: TextView

        override fun onInitialize() {
            ButterKnife.bind(this, itemView)
        }

        override fun onBind(position: Int, viewType: Int) {

            val item = getItem(position)

            if (item is StoryItemModel) {

//                // 设置信息
//                Glide.with(context)
//                        .load(item.images[0])
//                        .into(ivImage)
            }

//            tvTitle.text = item.title
//            tvSource.text = item.source
        }

        @OnClick(R2.id.card_news_item)
        fun onClick(view: View) {
            // 回调点击事件
            onItemEvent(0, view, adapterPosition)
        }
    }
}