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

package com.sky.android.news.presenter

import android.text.TextUtils
import com.sky.android.common.util.Alog
import com.sky.android.news.base.BaseSubscriber
import com.sky.android.news.contract.HeadLineContract
import com.sky.android.news.data.model.CategoryItemModel
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.source.NewsDataSource
import com.sky.android.news.ui.helper.PageHelper

/**
 * Created by sky on 17-9-22.
 */
class HeadLinePresenter(val source: NewsDataSource,
                        val view: HeadLineContract.View) : AbstractPresenter(), HeadLineContract.Presenter {

    private lateinit var mItem: CategoryItemModel
    private val mPageHelper = PageHelper<LineItemModel>()

    init {
        mPageHelper.notFixed = true
    }

    override fun setCategoryItem(item: CategoryItemModel) {
        mItem = item
    }

    override fun loadHeadLine() {
        // 加载
        loadHeadLine(0, false)
    }

    override fun loadMoreHeadLine() {

        if (!mPageHelper.isNextPage()) {
            view.cancelLoading()
            return
        }

        // 加载更多数据
        loadHeadLine(mPageHelper.getCurPage() + 1, true)
    }

    private fun loadHeadLine(curPage: Int, loadMore: Boolean) {

        view.showLoading()

        val start = curPage * PageHelper.PAGE_SIZE

        val observable = source.getHeadLine(mItem.tid,
                start, start + PageHelper.PAGE_SIZE)
                .doOnNext {

                    // 删除不运行的新闻
                    var tempList = ArrayList<LineItemModel>()

                    it.lineItems.forEach {
                        // 添加有效的新闻
                        if (TextUtils.isEmpty(it.template)) tempList.add(it)
                    }

                    it.lineItems = tempList
                }

        ioToMain(observable)
                .subscribe(HeadLineSubscriber(curPage, loadMore))
    }

    private inner class HeadLineSubscriber(val curPage: Int, val loadMore: Boolean) : BaseSubscriber<HeadLineModel>() {

        override fun onError(msg: String, tr: Throwable): Boolean {
            // 加载失败
            Alog.e(msg, tr)
            view.cancelLoading()
            view.showMessage("加载列表信息失败")
            return true
        }

        override fun onNext(model: HeadLineModel) {

            // 加载完成
            view.cancelLoading()

            if (loadMore) {
                // 追加数据
                mPageHelper.appendData(model.lineItems)
            } else {
                // 设置数据
                mPageHelper.setData(model.lineItems)
            }
            view.onLoadHeadLine(mPageHelper.getData())
        }
    }
}