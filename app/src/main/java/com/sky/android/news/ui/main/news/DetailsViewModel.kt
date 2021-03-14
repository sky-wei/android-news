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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.ext.doFailure
import com.sky.android.news.ext.doSuccess
import com.sky.android.news.ui.base.NewsViewModel
import com.sky.android.news.ui.helper.DetailsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by sky on 2021-01-06.
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
        application: Application,
        private val source: INewsSource
) : NewsViewModel(application) {

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = mLoading

    private val mFailure = MutableLiveData<String>()
    val failure: LiveData<String> = mFailure

    private val mDetails = MutableLiveData<DetailsModel>()
    val details: LiveData<DetailsModel> = mDetails

    private val mDetailsHelper = DetailsHelper(application)


    fun loadDetails(docId: String) {

        launchOnUI {
            source.getDetails(docId)
                    .map {
                        it.doSuccess {
                            val content = it.models

                            // 转换结果
                            content.body = mDetailsHelper.replaceImage(content.body, content.img)
                            content.body = mDetailsHelper.replaceVideo(content.body, content.video)

                            // 截取时间
                            val index = content.pTime.indexOf(" ")

                            if (index != -1) {
                                content.pTime = content.pTime.substring(0, index)
                            }
                        }
                    }
                    .onStart { mLoading.value = true }
                    .onCompletion { mLoading.value = false }
                    .collect {
                        it.doFailure {
                            mFailure.value = "加载详情内容失败"
                        }.doSuccess {
                            mDetails.value = it
                        }
                    }
        }
    }
}