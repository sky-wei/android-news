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

import com.sky.android.common.utils.Alog
import com.sky.android.news.base.BaseSubscriber
import com.sky.android.news.contract.StoryListContract
import com.sky.android.news.data.model.BaseItemModel
import com.sky.android.news.data.model.StoryListModel
import com.sky.android.news.data.model.TopStoryListModel
import com.sky.android.news.data.source.ZhiHuDataSource
import com.sky.android.news.ui.helper.PageHelper

/**
 * Created by sky on 17-9-28.
 */
class StoryListPresenter(val source: ZhiHuDataSource,
                         val view: StoryListContract.View) : AbstractPresenter(), StoryListContract.Presenter {

    private val mPageHelper = PageHelper<BaseItemModel>()
    private var mDate = ""

    init {
        mPageHelper.notFixed = true
    }

    override fun loadLastStories() {

        ioToMain(source.getLatestStories())
                .subscribe(StoriesSubscriber(false))
    }

    override fun loadMoreStories() {

        ioToMain(source.getStories(mDate))
                .subscribe(StoriesSubscriber(true))
    }

    private inner class StoriesSubscriber(val loadMore: Boolean) : BaseSubscriber<StoryListModel>() {

        override fun onError(msg: String, tr: Throwable): Boolean {
            // 加载失败
            Alog.e(msg, tr)
            view.cancelLoading()
            view.showMessage("加载列表信息失败")
            return true
        }

        override fun onNext(model: StoryListModel?) {

            if (model == null) {
                // 返回数据为空
                view.onLoadFailed("服务返回数据为空")
                return
            }

            // 加载完成
            view.cancelLoading()

            // 保存时间
            mDate = model.date

            if (loadMore) {
                // 追加数据
                mPageHelper.appendData(model.stories)
            } else {
                // 设置数据
                mPageHelper.setData(listOf(TopStoryListModel(model.topStories)))
                mPageHelper.appendData(model.stories)
            }
            view.onLoadStories(mPageHelper.getData())
        }
    }
}