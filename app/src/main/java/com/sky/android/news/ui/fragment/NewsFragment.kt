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
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import com.google.gson.Gson
import com.sky.android.news.R
import com.sky.android.news.base.VBaseFragment
import com.sky.android.news.contract.HeadLineContract
import com.sky.android.news.data.model.CategoryItemModel
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.source.NewsDataRepository
import com.sky.android.news.data.source.NewsSourceFactory
import com.sky.android.news.presenter.HeadLinePresenter

/**
 * Created by sky on 17-9-21.
 */
class NewsFragment : VBaseFragment(), HeadLineContract.View {

    @BindView(R.id.swip_refresh_layout)
    lateinit var swipRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    lateinit var mHeadLinePresenter: HeadLineContract.Presenter

    override fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun initView(view: View, args: Bundle?) {

        val repository = NewsDataRepository(NewsSourceFactory(context))
        mHeadLinePresenter = HeadLinePresenter(repository, this)

        mHeadLinePresenter.setCategoryItem(args!!.getSerializable("item") as CategoryItemModel)
        mHeadLinePresenter.loadHeadLine()
    }

    override fun onHeadLine(model: HeadLineModel) {

        println(">>>>>>>>>>>>>>> ${Gson().toJson(model)}")
    }

    override fun onLoadFailed(msg: String) {
    }
}