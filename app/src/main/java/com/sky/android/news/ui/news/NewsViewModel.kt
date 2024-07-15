/*
 * Copyright (c) 2024 The sky Authors.
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

package com.sky.android.news.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sky.android.common.util.Alog
import com.sky.android.news.R
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.model.XResult
import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.ext.doFailure
import com.sky.android.news.ext.doSuccess
import com.sky.android.news.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class NewsUiState(
    val category: CategoryModel? = null,
    val headLine: HeadLineModel? = null,
    val isLoading: Boolean = false,
    val message: Int? = null,
)

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    private val newsSource: INewsSource
) : AndroidViewModel(application) {

    private val _isLoading = MutableStateFlow(false)
    private val _headLine = MutableStateFlow<HeadLineModel?>(null)
    private val _loadCategory = newsSource.getCategory()

    val uiState: StateFlow<NewsUiState> = combine(
        _isLoading, _loadCategory, _headLine,
    ) { isLoading, loadCategory, headLine ->
        Alog.d(">>>>>>>>>>>>>>>>>>>>>> $isLoading  $loadCategory")
        Alog.d(">>>>>>>>>>>>>>>>>>>>>> $headLine")
        when(loadCategory) {
            is XResult.Success -> {
               NewsUiState(
                   category = loadCategory.value,
                   headLine = headLine,
                   isLoading = isLoading
               )
            }
            is XResult.Failure -> {
                NewsUiState(
                    message = R.string.loading
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = NewsUiState(isLoading = true)
    )

    fun messageShown() {

    }

    fun pageChange(page: Int) {
        viewModelScope.launch {
            val value = withContext(Dispatchers.IO) {
                newsSource.getHeadLine(
                    uiState.value.category!!.items[page].tid, 0, 20
                ).single()
            }
            value.doSuccess { data ->
                Alog.d(">>>>>>>>>>yyy $data")
                _headLine.value = data
            }
        }
    }
}