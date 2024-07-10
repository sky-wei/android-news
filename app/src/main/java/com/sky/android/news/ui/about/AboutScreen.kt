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

package com.sky.android.news.ui.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky.android.news.BuildConfig
import com.sky.android.news.R
import com.sky.android.news.ui.common.NewsBackTopAppBar
import com.sky.android.news.ui.theme.NewsTheme
import com.sky.android.news.util.ActivityUtil

@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NewsBackTopAppBar(
                onBack = onBack,
                title = stringResource(id = R.string.about)
            )
        }
    ) { innerPadding ->

        ProjectInfoItem(innerPadding) {

            val uri = Uri.parse(it)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            ActivityUtil.startActivity(context, intent)
        }
    }
}

@Composable
fun ProjectInfoItem(
    innerPadding: PaddingValues,
    onOpenLink: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        AppLogoWidget()
        Spacer(modifier = Modifier.height(20.dp))
        AppInfoWidget(onOpenLink = onOpenLink)
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(20.dp))
        CopyrightWidget()
    }
}

@Composable
private fun AppLogoWidget() {
    Image(
        modifier = Modifier
            .size(76.dp)
            .clip(CircleShape),
        painter = painterResource(id = R.drawable.ic_side_nav_head),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = stringResource(id = R.string.app_name),
//        color = MaterialTheme.customScheme.appThemeColor,
        fontSize = 24.sp
    )
}

@Composable
private fun AppInfoWidget(
    onOpenLink: (String) -> Unit
) {
    Text(
        text = stringResource(id = R.string.mail_x, stringResource(id = R.string.mail)),
        fontSize = 14.sp
    )
    Spacer(modifier = Modifier.height(10.dp))
    ClickableText(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface
                )
            ) {
                append(stringResource(id = R.string.source_address, "https://github.com/jingcai-wei"))
            }
        },
        style = TextStyle(
            textDecoration = TextDecoration.Underline,
            fontSize = 14.sp
        ),
        onClick = {
            onOpenLink("https://github.com/jingcai-wei/android-news")
        }
    )
}

@Composable
private fun CopyrightWidget() {
    Text(
        text = stringResource(id = R.string.version_x, BuildConfig.VERSION_NAME),
        fontSize = 10.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "Copyright © 2024 sky.All Rights Reserved.",
        fontSize = 10.sp
    )
    Spacer(modifier = Modifier.height(10.dp))
}


@Preview
@Composable
private fun ProjectInfoItemPreview() {
    NewsTheme {
        ProjectInfoItem(
            innerPadding = PaddingValues(2.dp),
            onOpenLink = {

            }
        )
    }
}