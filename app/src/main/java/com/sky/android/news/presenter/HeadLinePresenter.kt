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
import com.sky.android.news.contract.HeadLineContract
import com.sky.android.news.data.model.CategoryItemModel
import com.sky.android.news.data.source.NewsDataSource

/**
 * Created by sky on 17-9-22.
 */
class HeadLinePresenter(val source: NewsDataSource,
                        val view: HeadLineContract.View) : AbstractPresenter(), HeadLineContract.Presenter {

    private val TAG = "HeadLinePresenter"

    private lateinit var mItem: CategoryItemModel

    override fun setCategoryItem(item: CategoryItemModel) {
        mItem = item
    }

    override fun loadHeadLine() {

        ioToMain(source.getHeadLine(mItem.tid, 0, 20))
                .subscribe(
                        {
                            // 加载完成
                            view.onHeadLine(it)
                        },
                        {
                            // 加载失败
                            view.showMessage("加载列表信息失败")
                            Alog.e(TAG, "加载列表信息异常", it)
                        }
                )
    }

    override fun loadMoreHeadLine() {

    }
}