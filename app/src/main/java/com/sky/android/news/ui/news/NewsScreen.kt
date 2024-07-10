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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sky.android.news.ui.common.NewsTopAppBar


private enum class DemoTabs(val value: String) {
    APPLE("Apple"),
    GOOGLE("Google"),
    GOOGLE1("Google1"),
    GOOGLE2("Google2"),
    GOOGLE3("Google2"),
    GOOGLE4("Google2"),
    AMAZON("Amazon")
}

@Composable
fun NewsScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {

    val tabsName = remember { DemoTabs.entries.map { it.value } }
    val selectedIndex = remember { mutableIntStateOf(DemoTabs.APPLE.ordinal) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NewsTopAppBar(
                openDrawer = openDrawer
            )
        }
    ) { innerPadding ->

        Column(
            modifier.padding(innerPadding)
        ) {
            ScrollableTabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = selectedIndex.intValue,

            ) {
                tabsName.forEachIndexed { index, title ->
                    Tab(
                        selected = index == selectedIndex.intValue,
                        onClick = {
                            when (title) {
                                DemoTabs.APPLE.value -> {
                                    selectedIndex.intValue = DemoTabs.APPLE.ordinal
                                }
                                DemoTabs.GOOGLE.value -> {
                                    selectedIndex.intValue = DemoTabs.GOOGLE.ordinal
                                }
                                DemoTabs.AMAZON.value -> {
                                    selectedIndex.intValue = DemoTabs.AMAZON.ordinal
                                }
                            }
                        },
                        text = {
                            Text(title, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    )
                }
            }
        }
    }
}