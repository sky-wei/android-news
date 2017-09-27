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
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.sky.android.common.adapter.SimpleRecyclerAdapter
import com.sky.android.common.base.BaseRecyclerAdapter
import com.sky.android.common.base.BaseRecyclerHolder
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.data.model.LineItemModel

/**
 * Created by sky on 17-9-22.
 */
class NewsAdapter(context: Context) : SimpleRecyclerAdapter<LineItemModel>(context) {

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup, viewType: Int): View {
        return layoutInflater.inflate(R.layout.item_line_list, viewGroup, false)
    }

    override fun onCreateViewHolder(view: View, viewType: Int): BaseRecyclerHolder<LineItemModel> {
        return NewsHolder(view, this)
    }

    inner class NewsHolder(itemView: View, adapter: BaseRecyclerAdapter<LineItemModel>)
        : BaseRecyclerHolder<LineItemModel>(itemView, adapter) {

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

            // 设置信息
            Glide.with(context)
                    .load(item.imgSrc)
                    .into(ivImage)

            tvTitle.text = if (TextUtils.isEmpty(item.lTitle)) item.title else item.lTitle
            tvSource.text = item.source
        }

        @OnClick(R2.id.card_news_item)
        fun onClick(view: View) {
            // 回调点击事件
            onItemEvent(0, view, adapterPosition)
        }
    }
}