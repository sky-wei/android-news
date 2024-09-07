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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sky.android.news.data.model.CategoryItemModel
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.ui.common.LoadingBox
import com.sky.android.news.ui.common.LoadingContent
import com.sky.android.news.ui.common.NewsTopAppBar
import com.sky.android.news.ui.common.NoDataContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
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

        NewsContent(
            loading = uiState.loading,
            category = uiState.category,
            modifier = modifier.padding(innerPadding)
        )

        uiState.message?.let {
            val message = stringResource(id = it)
            LaunchedEffect(snackBarState, viewModel, message) {
                snackBarState.showSnackbar(message)
                viewModel.messageShown()
            }
        }
    }
}

@Composable
private fun NewsContent(
    loading: Boolean,
    category: CategoryModel?,
    modifier: Modifier
) {
    LoadingContent(
        loading = loading,
        loadingContent = { LoadingBox() }
    ) {
        category?.let {
            NewsContent(
                items = it.items,
                modifier = modifier
            )
        } ?: NoDataContent()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NewsContent(
    items: List<CategoryItemModel>,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {

        val pagerState = rememberPagerState(initialPage = 0) {
            items.size
        }

        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            TabItem(
                items = items,
                currentPage = pagerState.currentPage
            ) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        }

        HorizontalPager(state = pagerState) { page ->
            ListScreen(
                item = items[page]
            )
        }

//        LaunchedEffect(pagerState) {
//            snapshotFlow { pagerState.currentPage }.collect { page ->
//                pageChange(page)
//            }
//        }
    }
}


@Composable
private fun TabItem(
    items: List<CategoryItemModel>,
    currentPage: Int,
    scrollToPage: (Int) -> Unit
) {
    items.forEachIndexed { index, item ->
        Tab(
            selected = index == currentPage,
            onClick = {
                scrollToPage(index)
            },
            text = {
                Text(
                    item.name,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        )
    }
}
