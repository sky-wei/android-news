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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sky.android.common.util.Alog
import com.sky.android.news.data.model.news.CategoryItemModel
import com.sky.android.news.data.model.news.LineItemModel
import com.sky.android.news.data.repository.news.INewsRepository
import com.sky.android.news.ext.doSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class NewsListUiState(
    val lineItems: List<LineItemModel>? = null
)

@HiltViewModel
class ListViewModel @Inject constructor(
    application: Application,
    private val newsRepository: INewsRepository
) : AndroidViewModel(application) {

    private val _categoryItem = MutableLiveData<CategoryItemModel?>()
    val categoryItem: LiveData<CategoryItemModel?>
        get() = _categoryItem

    private val _lineItems = MutableStateFlow<List<LineItemModel>>(arrayListOf())

    private val _uiState: MutableStateFlow<NewsListUiState> = MutableStateFlow(NewsListUiState())
    val uiState: StateFlow<NewsListUiState> = _uiState.asStateFlow()

    fun initCategory(item: CategoryItemModel) {
        Alog.d(">>>>>>>>>>>>>>> initCategory $item")
        _categoryItem.value = item
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val value = withContext(Dispatchers.IO) {
                newsRepository.getHeadLine(
                    _categoryItem.value!!.tid, 0, 20
                ).single()
            }
            value.doSuccess { data ->
                Alog.d(">>>>>>>>>>yyy $data")
                _uiState.update { it.copy(lineItems = data.lineItems) }
            }
        }
        Alog.d(">>>>>>>>>>>>>>>>>>>>> ${_categoryItem.value}")
    }
}