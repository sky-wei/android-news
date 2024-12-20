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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sky.android.news.R
import com.sky.android.news.data.model.story.StoryDetailsModel
import com.sky.android.news.ext.getError
import com.sky.android.news.ui.component.ErrorView
import com.sky.android.news.ui.component.LoadingView
import com.sky.android.news.ui.component.NewsBackTopAppBar

/**
 * Created by sky on 11/25/24.
 */

@Composable
fun StoryDetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StoryDetailViewModel = hiltViewModel()
) {
    val snackBarState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NewsBackTopAppBar(
                onBack = onBack,
                title = stringResource(R.string.story),
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarState)
        }
    ) { innerPadding ->

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when(uiState) {
                is StoryDetailUIState.Loading -> {
                    LoadingView(
                        modifier = modifier.fillMaxSize()
                    )
                }
                is StoryDetailUIState.Success -> {
                    StoryDetailsContent(
                        detail = (uiState as StoryDetailUIState.Success).detail,
                        modifier = modifier.fillMaxSize()
                    )
                }
                is StoryDetailUIState.Error -> {
                    val error = (uiState as StoryDetailUIState.Error)
                        .remoteSourceException
                        .getError(LocalContext.current)
                    ErrorView(
                        errorText = error,
                        modifier = modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun StoryDetailsContent(
    detail: StoryDetailsModel,
    modifier: Modifier,
) {
    Text(
        detail.body
    )
}