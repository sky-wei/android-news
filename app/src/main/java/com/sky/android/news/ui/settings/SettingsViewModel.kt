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

package com.sky.android.news.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import com.sky.android.news.R
import com.sky.android.news.data.cache.CacheManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val message: Int? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun clearNewsCache() {
        viewModelScope.launch {
            // 清除新闻缓存
            CacheManager.getInstance(getApplication()).clear()
            showMessage(R.string.clear_success)
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    fun clearImageCache() {
        viewModelScope.launch {
            // 清除图片缓存
            Coil.imageLoader(getApplication()).run {
                memoryCache?.clear()
                diskCache?.clear()
            }
            showMessage(R.string.clear_success)
        }
    }

    fun messageShown() {
        _uiState.update { it.copy(message = null) }
    }

    private fun showMessage(message: Int) {
        _uiState.update { it.copy(message = message) }
    }
}