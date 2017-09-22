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
import com.sky.android.news.base.VBaseFragment
import com.sky.android.news.contract.HeadLineContract
import com.sky.android.news.data.model.CategoryItemModel
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.source.NewsDataRepository
import com.sky.android.news.data.source.NewsSourceFactory
import com.sky.android.news.presenter.HeadLinePresenter
import com.sky.android.news.ui.adapter.NewsAdapter
import com.sky.android.news.ui.helper.RecyclerHelper

/**
 * Created by sky on 17-9-21.
 */
class NewsFragment : VBaseFragment(), HeadLineContract.View, OnItemEventListener, RecyclerHelper.OnCallback {

    @BindView(R.id.swip_refresh_layout)
    lateinit var swipRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    lateinit var mHeadLinePresenter: HeadLineContract.Presenter
    lateinit var mRecyclerHelper: RecyclerHelper

    private lateinit var mNewsAdapter: NewsAdapter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun initView(view: View, args: Bundle?) {

        mNewsAdapter = NewsAdapter(context)
        mNewsAdapter.onItemEventListener = this

        swipRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mNewsAdapter

        val repository = NewsDataRepository(NewsSourceFactory(context))
        mHeadLinePresenter = HeadLinePresenter(repository, this)

        // 刷新助手类
        mRecyclerHelper = RecyclerHelper(swipRefreshLayout, recyclerView, this)
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

    override fun onHeadLine(model: List<LineItemModel>) {

        // 更新列表信息
        mNewsAdapter.items = model
        mNewsAdapter.notifyDataSetChanged()
    }

    override fun onLoadFailed(msg: String) {
        showMessage(msg)
    }

    override fun onItemEvent(event: Int, view: View, position: Int, vararg args: Any?) {

    }

    override fun onRefresh() {
        // 重新加载数据
        mHeadLinePresenter.loadHeadLine()
    }

    override fun onLoadMore() {
        mHeadLinePresenter.loadMoreHeadLine()
    }
}