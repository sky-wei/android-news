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
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sky.android.core.adapter.BaseRecyclerAdapter
import com.sky.android.core.adapter.BaseRecyclerHolder
import com.sky.android.core.adapter.SimpleRecyclerAdapter
import com.sky.android.news.R
import com.sky.android.news.data.model.LineItemModel
import kotlinx.android.synthetic.main.item_line_list.view.*

/**
 * Created by sky on 17-9-22.
 */
class NewsAdapter(context: Context) : SimpleRecyclerAdapter<LineItemModel>(context) {

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, viewType: Int): View =
            layoutInflater.inflate(R.layout.item_line_list, viewGroup, false)

    override fun onCreateViewHolder(view: View, viewType: Int): BaseRecyclerHolder<LineItemModel> =
            NewsHolder(view, this)

    inner class NewsHolder(itemView: View, adapter: BaseRecyclerAdapter<LineItemModel>)
        : BaseRecyclerHolder<LineItemModel>(itemView, adapter) {

        override fun onInitialize() {
            super.onInitialize()

            itemView.card_news_item.setOnClickListener {
                // 回调点击事件
                callItemEvent(it, adapterPosition)
            }
        }

        override fun onBind(position: Int, viewType: Int) {

            val item = getItem(position)

            // 设置信息
            Glide.with(context)
                    .load(item.imgSrc)
                    .into(itemView.iv_image)

            itemView.tv_title.text = if (TextUtils.isEmpty(item.lTitle)) item.title else item.lTitle
            itemView.tv_source.text = item.source
        }
    }
}