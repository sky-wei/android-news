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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sky.android.news.data.model.story.StoryItemModel
import com.sky.android.news.data.model.story.StoryListModel
import com.sky.android.news.data.model.story.TopStoryItemModel
import com.sky.android.news.ext.getError
import com.sky.android.news.ui.component.ErrorView
import com.sky.android.news.ui.component.LoadingView
import com.sky.android.news.ui.component.NewsTopAppBar
import com.sky.android.news.ui.component.NoDataContent
import com.sky.android.news.ui.component.StoryCarousel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StoryViewModel = hiltViewModel()
) {

    val snackBarState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NewsTopAppBar(
                scrollBehavior = scrollBehavior,
                openDrawer = openDrawer
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarState)
        }
    ) { innerPadding ->

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when(uiState) {
                is StoryUiState.Loading -> {
                    LoadingView(
                        modifier = modifier.fillMaxSize()
                    )
                }
                is StoryUiState.Success -> {
                    StoryContent(
                        (uiState as StoryUiState.Success).storyList,
                        modifier = modifier.fillMaxSize()
                    )
                }
                is StoryUiState.Error -> {
                    val error = (uiState as StoryUiState.Error)
                        .remoteSourceException
                        .getError(LocalContext.current)
                    ErrorView(
                        errorText = error,
                        modifier = modifier.fillMaxSize()
                    )
                }
            }
        }

//        uiState.message?.let {
//            val message = stringResource(id = it)
//            LaunchedEffect(snackBarState, viewModel, message) {
//                snackBarState.showSnackbar(message)
//                viewModel.messageShown()
//            }
//        }
    }
}

@Composable
private fun StoryContent(
    storyList: StoryListModel?,
    modifier: Modifier
) {
    storyList?.let {
        StoreContent(
            data = storyList.date,
            topStories = storyList.topStories,
            stories = storyList.stories,
            modifier = modifier
        )
    } ?: NoDataContent()
}

@Composable
private fun StoreContent(
    data: String,
    topStories: List<TopStoryItemModel>,
    stories: List<StoryItemModel>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        StoryCarousel(
            topStories = topStories,
            onItemClicked = {  }
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            items(
                count = stories.size,
                key = { index -> stories[index].id.toString() }
            ) { index ->
                VerticalListItem(stories[index])
                if (index + 1 != stories.size) {
                    ListItemDivider()
                }
            }
        }
    }
}





@Composable
private fun VerticalListItem(
    item: StoryItemModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(3.dp))
            .clickable {  }
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(3.dp)),
            model = item.images[0],
            contentDescription = null
        )
        Text(
            text = item.title,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp),
        )
    }
}

@Composable
private fun ListItemDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 15.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}