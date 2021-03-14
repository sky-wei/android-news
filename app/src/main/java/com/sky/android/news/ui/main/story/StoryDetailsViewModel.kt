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
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.source.IStorySource
import com.sky.android.news.ext.doFailure
import com.sky.android.news.ext.doSuccess
import com.sky.android.news.ui.base.NewsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by sky on 2021-01-25.
 */
@HiltViewModel
class StoryDetailsViewModel @Inject constructor(
        application: Application,
        private val source: IStorySource
) : NewsViewModel(application) {

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = mLoading

    private val mFailure = MutableLiveData<String>()
    val failure: LiveData<String> = mFailure

    private val mDetails = MutableLiveData<StoryDetailsModel>()
    val details: LiveData<StoryDetailsModel> = mDetails

    fun loadDetails(id: Long) {

        launchOnUI {

            source.getStory(id.toString())
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