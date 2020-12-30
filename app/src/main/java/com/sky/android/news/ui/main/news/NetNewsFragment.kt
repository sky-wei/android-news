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

package com.sky.android.news.ui.main.news

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.hi.dhl.binding.viewbind
import com.sky.android.core.interfaces.OnItemEventListener
import com.sky.android.news.R
import com.sky.android.news.contract.HeadLineContract
import com.sky.android.news.data.model.CategoryItemModel
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.source.NewsDataRepository
import com.sky.android.news.data.source.NewsSourceFactory
import com.sky.android.news.databinding.FragmentNewsBinding
import com.sky.android.news.presenter.HeadLinePresenter
import com.sky.android.news.ui.base.NewsFragment
import com.sky.android.news.ui.helper.RecyclerHelper
import com.sky.android.news.util.ActivityUtil

/**
 * Created by sky on 17-9-21.
 */
class NetNewsFragment : NewsFragment(), HeadLineContract.View, OnItemEventListener, RecyclerHelper.OnCallback {

    private val binding: FragmentNewsBinding by viewbind()

    private lateinit var mHeadLinePresenter: HeadLineContract.Presenter
    private lateinit var mRecyclerHelper: RecyclerHelper

    private lateinit var mNewsAdapter: NewsAdapter

    override val layoutId: Int
        get() = R.layout.fragment_news

    override fun initView(view: View, args: Bundle?) {

        mNewsAdapter = NewsAdapter(context)
        mNewsAdapter.onItemEventListener = this

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = mNewsAdapter
        }

        val repository = NewsDataRepository(NewsSourceFactory(context))
        mHeadLinePresenter = HeadLinePresenter(repository, this)

        // 刷新助手类
        mRecyclerHelper = RecyclerHelper(binding.swipeRefreshLayout, binding.recyclerView, this)
        mRecyclerHelper.setLoadMore(true)
        mRecyclerHelper.forceRefreshing()

        mHeadLinePresenter.setCategoryItem(args!!.getSerializable("item") as CategoryItemModel)
        mHeadLinePresenter.loadHeadLine()
    }

    override fun showLoading() {
    }

    override fun cancelLoading() {
        mRecyclerHelper.cancelRefreshing()
    }

    override fun onLoadHeadLine(model: List<LineItemModel>) {

        // 更新列表信息
        mNewsAdapter.items = model
        mNewsAdapter.notifyDataSetChanged()
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }

    override fun onItemEvent(event: Int, view: View, position: Int, vararg args: Any?): Boolean {

        // 进入详情界面
        ActivityUtil.startDetailsActivity(context,
                DetailsFragment::class.java, mNewsAdapter.getItem(position))
        return true
    }

    override fun onRefresh() {
        // 重新加载数据
        mHeadLinePresenter.loadHeadLine()
    }

    override fun onLoadMore() {
        mHeadLinePresenter.loadMoreHeadLine()
    }
}