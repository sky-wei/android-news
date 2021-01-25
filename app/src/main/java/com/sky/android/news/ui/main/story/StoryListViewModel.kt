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

package com.sky.android.news.ui.main.story

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sky.android.news.data.model.BaseViewType
import com.sky.android.news.data.model.NodeItemModel
import com.sky.android.news.data.model.TopStoryListModel
import com.sky.android.news.data.source.RepositoryFactory
import com.sky.android.news.ext.doFailure
import com.sky.android.news.ext.doSuccess
import com.sky.android.news.ui.base.NewsViewModel
import com.sky.android.news.ui.helper.PageHelper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * Created by sky on 2021-01-25.
 */
class StoryListViewModel(
        application: Application
) : NewsViewModel(application) {

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = mLoading

    private val mFailure = MutableLiveData<String>()
    val failure: LiveData<String> = mFailure

    private val mStoryList = MutableLiveData<List<BaseViewType>>()
    val storyList: LiveData<List<BaseViewType>> = mStoryList

    private val mRepository by lazy { RepositoryFactory.create(application).createStorySource() }

    private val mPageHelper = PageHelper<BaseViewType>()
    private var mDate = ""

    init {
        mPageHelper.notFixed = true
    }

    fun loadLastStories() {

        launchOnUI {

            mRepository.getLatestStories()
                    .onStart { mLoading.value = true }
                    .onCompletion { mLoading.value = false }
                    .collect {

                        it.doFailure {
                            mFailure.value = "加载列表信息失败"
                        }.doSuccess {
                            // 保存时间
                            mDate = it.date
                            // 设置数据
                            mPageHelper.setData(listOf(TopStoryListModel(it.topStories)))
                            mPageHelper.appendData(listOf(NodeItemModel(it.date, "今日热闻")))
                            mPageHelper.appendData(it.stories)
                            mStoryList.value = mPageHelper.getData()
                        }
                    }
        }
    }

    fun loadMoreStories() {

        launchOnUI {

            mRepository.getLatestStories()
                    .onStart { mLoading.value = true }
                    .onCompletion { mLoading.value = false }
                    .collect {

                        it.doFailure {
                            mFailure.value = "加载列表信息失败"
                        }.doSuccess {
                            // 保存时间
                            mDate = it.date
                            // 追加数据
                            mPageHelper.appendData(listOf(NodeItemModel(it.date)))
                            mPageHelper.appendData(it.stories)
                            mStoryList.value = mPageHelper.getData()
                        }
                    }
        }
    }
}