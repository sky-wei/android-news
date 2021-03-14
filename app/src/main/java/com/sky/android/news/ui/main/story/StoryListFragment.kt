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

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.hi.dhl.binding.viewbind
import com.sky.android.core.interfaces.OnItemEventListener
import com.sky.android.news.R
import com.sky.android.news.databinding.FragmentZhihuBinding
import com.sky.android.news.ui.base.NewsFragment
import com.sky.android.news.ui.helper.RecyclerHelper
import com.sky.android.news.util.ActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

/**
 * Created by sky on 17-9-28.
 */
@AndroidEntryPoint
class StoryListFragment : NewsFragment(), OnItemEventListener, RecyclerHelper.OnCallback {

    private val binding: FragmentZhihuBinding by viewbind()
    private val viewModel by viewModels<StoryListViewModel>()

    private lateinit var mRecyclerHelper: RecyclerHelper

    private lateinit var mStoryAdapter: StoryAdapter

    override val layoutId: Int
        get() = R.layout.fragment_zhihu

    override fun initView(view: View, args: Bundle?) {

        mStoryAdapter = StoryAdapter(context)
        mStoryAdapter.onItemEventListener = this

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = mStoryAdapter
        }

        // 刷新助手类
        mRecyclerHelper = RecyclerHelper(binding.swipeRefreshLayout, binding.recyclerView, this)
        mRecyclerHelper.setLoadMore(true)
        mRecyclerHelper.forceRefreshing()

        viewModel.apply {

            loading.observe(this@StoryListFragment) {
                if (!it) cancelLoading()
            }
            failure.observe(this@StoryListFragment) {
                showMessage(it)
            }
            storyList.observe(this@StoryListFragment) {
                mStoryAdapter.items = it
                mStoryAdapter.notifyDataSetChanged()
            }
        }

        // 加载最后一个信息
        viewModel.loadLastStories()
    }

    override fun showLoading() {
    }

    override fun cancelLoading() {
        mRecyclerHelper.cancelRefreshing()
    }

    override fun onItemEvent(event: Int, view: View, position: Int, vararg args: Any?): Boolean {

        if (event == 1) {
            // 进入详情界面
            ActivityUtil.startDetailsActivity(context,
                    StoryDetailsFragment::class.java, args[0] as Serializable)
            return true
        }

        // 进入详情界面
        ActivityUtil.startDetailsActivity(context,
                StoryDetailsFragment::class.java, mStoryAdapter.getItem(position))
        return true
    }

    override fun onRefresh() {
        // 重新加载数据
        viewModel.loadLastStories()
    }

    override fun onLoadMore() {
        viewModel.loadMoreStories()
    }
}