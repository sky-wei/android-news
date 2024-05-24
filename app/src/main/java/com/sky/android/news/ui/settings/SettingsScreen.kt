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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sky.android.news.R
import com.sky.android.news.ui.common.NewsBackTopAppBar
import com.sky.android.news.ui.theme.NewsTheme


@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NewsBackTopAppBar(
                onBack = onBack,
                title = stringResource(id = R.string.setting)
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.Start
        ) {
            StorageSettingWidget(viewModel)
        }
    }
}

@Composable
private fun StorageSettingWidget(
    viewModel: SettingsViewModel
) {
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        modifier = Modifier.padding(start = 20.dp),
        text = stringResource(id = R.string.storage_setting),
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(10.dp))
    SettingItemWidget(
        text = stringResource(id = R.string.clear_news_cache)
    ) {
        viewModel.clearNewsCache()
    }
    SettingItemWidget(
        text = stringResource(id = R.string.clear_image_cache)
    ) {
        viewModel.clearImageCache()
    }
}

@Composable
private fun SettingItemWidget(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Icon(
            Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Preview
@Composable
private fun StorageSettingWidgetPreview() {
    NewsTheme {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            StorageSettingWidget(
                viewModel = hiltViewModel()
            )
        }
    }
}