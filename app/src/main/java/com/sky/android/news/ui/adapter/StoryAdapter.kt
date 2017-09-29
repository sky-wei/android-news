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
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.listener.OnItemClickListener
import com.bumptech.glide.Glide
import com.sky.android.common.adapter.SimpleRecyclerAdapter
import com.sky.android.common.base.BaseRecyclerAdapter
import com.sky.android.common.base.BaseRecyclerHolder
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.data.model.*
import java.text.SimpleDateFormat

/**
 * Created by sky on 17-9-28.
 */
class StoryAdapter(context: Context) : SimpleRecyclerAdapter<BaseViewType>(context) {

    private val dateFormat = SimpleDateFormat("yyyyMMdd")
    private val dateFormat2 = SimpleDateFormat("MM月dd日 E")

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup, viewType: Int): View {
        return when(viewType) {
            0 -> layoutInflater.inflate(R.layout.item_top_story, viewGroup, false)
            1 -> layoutInflater.inflate(R.layout.item_story_list, viewGroup, false)
            else -> layoutInflater.inflate(R.layout.item_note_story, viewGroup, false)
        }
    }

    override fun onCreateViewHolder(view: View, viewType: Int): BaseRecyclerHolder<BaseViewType> {
        return when(viewType) {
            0 -> TopStoryHolder(view, this)
            1 -> StoryItemHolder(view, this)
            else -> NoteStoryHolder(view, this)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    inner class NoteStoryHolder(itemView: View, adapter: BaseRecyclerAdapter<BaseViewType>)
        : BaseRecyclerHolder<BaseViewType>(itemView, adapter) {

        @BindView(R2.id.tv_note)
        lateinit var tvNote: TextView

        override fun onInitialize() {
            ButterKnife.bind(this, itemView)
        }

        override fun onBind(position: Int, viewType: Int) {

            val item = getItem(position) as NodeItemModel

            val date = dateFormat.parse(item.data)

            tvNote.text = if (!TextUtils.isEmpty(item.node)) item.node else dateFormat2.format(date)
        }
    }

    inner class TopStoryHolder(itemView: View, adapter: BaseRecyclerAdapter<BaseViewType>)
        : BaseRecyclerHolder<BaseViewType>(itemView, adapter), OnItemClickListener {

        @BindView(R2.id.convenient_banner)
        lateinit var convenientBanner: ConvenientBanner<TopStoryItemModel>

        override fun onInitialize() {
            ButterKnife.bind(this, itemView)

            convenientBanner.setPageIndicator(
                    intArrayOf(R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused))
            convenientBanner.setPageIndicatorAlign(
                    ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
            convenientBanner.setOnItemClickListener(this)
        }

        override fun onBind(position: Int, viewType: Int) {

            val item = getItem(position) as TopStoryListModel

            convenientBanner.setPages(BannerHolderCreator(), item.topStories)
            convenientBanner.notifyDataSetChanged()
        }

        override fun onItemClick(position: Int) {
            val item = getItem(adapterPosition) as TopStoryListModel
            onItemEvent(1, itemView, adapterPosition, item.topStories[position])
        }

        override fun onAttachedToWindow() {
            super.onAttachedToWindow()
            // 开始滚动
            convenientBanner.startTurning(5000)
        }

        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            // 停止滚动
            convenientBanner.stopTurning()
        }
    }

    inner class StoryItemHolder(itemView: View, adapter: BaseRecyclerAdapter<BaseViewType>)
        : BaseRecyclerHolder<BaseViewType>(itemView, adapter) {

        @BindView(R2.id.iv_image)
        lateinit var ivImage: ImageView
        @BindView(R2.id.tv_title)
        lateinit var tvTitle: TextView

        override fun onInitialize() {
            ButterKnife.bind(this, itemView)
        }

        override fun onBind(position: Int, viewType: Int) {

            val item = getItem(position) as StoryItemModel

            Glide.with(context)
                    .load(item.images[0])
                    .into(ivImage)
            tvTitle.text = item.title
        }

        @OnClick(R2.id.card_zhihu_item)
        fun onClick(view: View) {
            // 回调点击事件
            onItemEvent(0, view, adapterPosition)
        }
    }
}