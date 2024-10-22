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

package com.sky.android.news.ui.story

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sky.android.news.R
import com.sky.android.news.data.model.XResult
import com.sky.android.news.data.model.story.StoryListModel
import com.sky.android.news.data.repository.story.IStoryRepository
import com.sky.android.news.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class StoryUiState(
    val storyList: StoryListModel? = null,
    val loading: Boolean = false,
    val message: Int? = null,
)

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    storyRepository: IStoryRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    private val _message = MutableStateFlow<Int?>(null)
    private val _loadStories = storyRepository.getLatestStories()

    val uiState: StateFlow<StoryUiState> = combine(
        _loading, _loadStories, _message,
    ) { loading, loadStories, message ->
        when(loadStories) {
            is XResult.Success -> {
                StoryUiState(
                    storyList = loadStories.value,
                    loading = loading,
                    message = message
                )
            }
            is XResult.Failure -> {
                StoryUiState(
                    message = R.string.loading
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = StoryUiState(loading = true)
    )

    fun messageShown() {
        _message.value = null
    }
}