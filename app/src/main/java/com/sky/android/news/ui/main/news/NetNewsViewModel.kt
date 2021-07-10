/*
 * Copyright (c) 2021 The sky Authors.
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

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sky.android.news.data.model.LineItemModel
import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.ext.doFailure
import com.sky.android.news.ext.doSuccess
import com.sky.android.news.ui.base.NewsViewModel
import com.sky.android.news.ui.helper.PageHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by sky on 2021-01-25.
 */
@HiltViewModel
class NetNewsViewModel @Inject constructor(
        application: Application,
        private val source: INewsSource
) : NewsViewModel(application) {

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = mLoading

    private val mFailure = MutableLiveData<String>()
    val failure: LiveData<String> = mFailure

    private val mLineItem = MutableLiveData<List<LineItemModel>>()
    val lineItem: LiveData<List<LineItemModel>> = mLineItem


    private val mPageHelper = PageHelper<LineItemModel>()

    var tid: String = ""

    fun loadHeadLine() {
        // 加载
        loadHeadLine(0, false)
    }

    fun loadMoreHeadLine() {

        if (!mPageHelper.isNextPage()) {
            mLoading.value = false
            return
        }

        // 加载更多数据
        loadHeadLine(mPageHelper.getCurPage() + 1, true)
    }

    private fun loadHeadLine(curPage: Int, loadMore: Boolean) {

        launchOnUI {

            val start = curPage * PageHelper.PAGE_SIZE

            source.getHeadLine(tid, start, start + PageHelper.PAGE_SIZE)
                    .map {
                        it.doSuccess {
                            // 删除不运行的新闻
                            var tempList = ArrayList<LineItemModel>()

                            it.lineItems.forEach {
                                // 添加有效的新闻
                                if (TextUtils.isEmpty(it.template)) tempList.add(it)
                            }

                            it.lineItems = tempList
                        }
                    }
                    .onStart { mLoading.value = true }
                    .onCompletion { mLoading.value = false }
                    .collect {

                        it.doFailure {
                            mFailure.value = "加载列表信息失败"
                        }.doSuccess {
                            if (loadMore) {
                                // 追加数据
                                mPageHelper.appendData(it.lineItems)
                            } else {
                                // 设置数据
                                mPageHelper.setData(100, it.lineItems)
                            }
                            mLineItem.value = mPageHelper.getData()
                        }
                    }
        }
    }
}