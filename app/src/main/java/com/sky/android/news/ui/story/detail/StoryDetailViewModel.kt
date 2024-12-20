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

package com.sky.android.news.ui.story.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sky.android.news.data.exception.RemoteSourceException
import com.sky.android.news.data.model.XResult
import com.sky.android.news.data.model.story.StoryDetailsModel
import com.sky.android.news.data.repository.story.IStoryRepository
import com.sky.android.news.ext.asResult
import com.sky.android.news.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Created by sky on 11/25/24.
 */
@HiltViewModel
class StoryDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    storyRepository: IStoryRepository
) : ViewModel() {

    val uiState: StateFlow<StoryDetailUIState> = storyRepository
        .getStory(savedStateHandle["id"]!!)
        .asResult()
        .map {
            when(it) {
                is XResult.Success -> {
                    StoryDetailUIState.Success(it.value)
                }
                is XResult.Error -> {
                    StoryDetailUIState.Error(it.remoteSourceException)
                }
                is XResult.Loading -> {
                    StoryDetailUIState.Loading
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = StoryDetailUIState.Loading
        )
}


sealed interface StoryDetailUIState {
    data class Success(val detail: StoryDetailsModel) : StoryDetailUIState
    data class Error(val remoteSourceException: RemoteSourceException) : StoryDetailUIState
    data object Loading : StoryDetailUIState
}