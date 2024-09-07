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
import com.sky.android.news.R
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.XResult
import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class NewsUiState(
    val category: CategoryModel? = null,
    val loading: Boolean = false,
    val message: Int? = null,
)

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    newsSource: INewsSource
) : AndroidViewModel(application) {

    private val _loading = MutableStateFlow(false)
    private val _message = MutableStateFlow<Int?>(null)
    private val _loadCategory = newsSource.getCategory()

    val uiState: StateFlow<NewsUiState> = combine(
        _loading, _loadCategory, _message,
    ) { loading, loadCategory, message ->
        when(loadCategory) {
            is XResult.Success -> {
               NewsUiState(
                   category = loadCategory.value,
                   loading = loading,
                   message = message
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
        initialValue = NewsUiState(loading = true)
    )

    fun messageShown() {
        _message.value = null
    }
}