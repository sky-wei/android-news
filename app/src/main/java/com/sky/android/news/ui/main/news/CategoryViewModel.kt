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
import androidx.lifecycle.MutableLiveData
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.source.RepositoryFactory
import com.sky.android.news.ext.doFailure
import com.sky.android.news.ext.doSuccess
import com.sky.android.news.ui.base.NewsViewModel
import kotlinx.coroutines.flow.collect

/**
 * Created by sky on 2021-01-06.
 */
class CategoryViewModel(
        application: Application
) : NewsViewModel(application) {

    private val mRepository by lazy { RepositoryFactory.create(application).createNewsSource() }

    val category = MutableLiveData<CategoryModel>()
    val message = MutableLiveData<String>()

    fun loadCategory() {

        launchOnUI{
            // 加载分类
            mRepository.getCategory()
                    .collect {
                        it.doSuccess {
                            category.value = it
                        }.doFailure {
                            message.value = "加载分类列表失败"
                        }
                    }
        }
    }
}