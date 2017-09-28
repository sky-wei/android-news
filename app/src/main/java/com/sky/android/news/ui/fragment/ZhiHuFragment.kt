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

package com.sky.android.news.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import com.sky.android.common.interfaces.OnItemEventListener
import com.sky.android.news.R
import com.sky.android.news.R2
import com.sky.android.news.contract.StoryListContract
import com.sky.android.news.data.model.ItemModel
import com.sky.android.news.data.source.ZhiHuDataRepository
import com.sky.android.news.data.source.ZhiHuSourceFactory
import com.sky.android.news.presenter.StoryListPresenter
import com.sky.android.news.ui.adapter.StoryAdapter
import com.sky.android.news.ui.base.VBaseFragment
import com.sky.android.news.ui.helper.RecyclerHelper

/**
 * Created by sky on 17-9-28.
 */
class ZhiHuFragment : VBaseFragment(), StoryListContract.View, OnItemEventListener, RecyclerHelper.OnCallback {

    @BindView(R2.id.swipe_refresh_layout)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R2.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    private lateinit var mStoryListPresenter: StoryListContract.Presenter
    private lateinit var mRecyclerHelper: RecyclerHelper

    private lateinit var mStoryAdapter: StoryAdapter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_zhihu, container, false)
    }

    override fun initView(view: View, args: Bundle?) {

        mStoryAdapter = StoryAdapter(context)
        mStoryAdapter.onItemEventListener = this

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mStoryAdapter

        val repository = ZhiHuDataRepository(ZhiHuSourceFactory(context))
        mStoryListPresenter = StoryListPresenter(repository, this)

        // 刷新助手类
        mRecyclerHelper = RecyclerHelper(swipeRefreshLayout, recyclerView, this)
        mRecyclerHelper.setLoadMore(true)
        mRecyclerHelper.forceRefreshing()

        mStoryListPresenter.loadLastStories()
    }

    override fun showLoading() {
    }

    override fun cancelLoading() {
        mRecyclerHelper.cancelRefreshing()
    }

    override fun onItemEvent(event: Int, view: View, position: Int, vararg args: Any?) {

//        val intent = Intent(context, DetailsActivity::class.java).apply {
//            putExtra("item", mNewsAdapter.getItem(position))
//        }

        // 进入详情界面
//        ActivityUtil.startActivity(context, intent)
    }

    override fun onLoadStories(model: List<ItemModel>) {

        mStoryAdapter.items = model
        mStoryAdapter.notifyDataSetChanged()
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }

    override fun onRefresh() {
        // 重新加载数据
        mStoryListPresenter.loadLastStories()
    }

    override fun onLoadMore() {
        mStoryListPresenter.loadMoreStories()
    }
}