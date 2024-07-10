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

package com.sky.android.news.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sky.android.news.R
import com.sky.android.news.ui.navigation.NewsDestinations
import com.sky.android.news.ui.navigation.NewsNavigationActions
import com.sky.android.news.ui.theme.NewsTheme
import com.sky.android.news.ui.theme.customScheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppModalDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NewsNavigationActions,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerSheet(
                currentRoute = currentRoute,
                navigateToNews = { navigationActions.navigateToNews() },
                navigateToStory = { navigationActions.navigateToStory() },
                navigateToSettings = { navigationActions.navigateToSettings() },
                navigateToAbout = { navigationActions.navigateToAbout() },
                closeDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        }
    ) {
        content()
    }
}

@Composable
private fun AppDrawerSheet(
    currentRoute: String,
    navigateToNews: () -> Unit,
    navigateToStory: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAbout: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier
    ) {
        DrawerHeader()
        Spacer(modifier = Modifier.height(20.dp))
        DrawerItem(
            painter = painterResource(id = R.drawable.ic_video_library_white),
            label = stringResource(id = R.string.news),
            isSelected = currentRoute == NewsDestinations.NEWS_ROUTE,
            action = {
                navigateToNews()
                closeDrawer()
            }
        )
        DrawerItem(
            painter = painterResource(id = R.drawable.ic_video_library_white),
            label = stringResource(id = R.string.story),
            isSelected = currentRoute == NewsDestinations.STORY_ROUTE,
            action = {
                navigateToStory()
                closeDrawer()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(20.dp))
        DrawerItem(
            painter = painterResource(id = R.drawable.ic_settings_applications_white),
            label = stringResource(id = R.string.setting),
            isSelected = currentRoute == NewsDestinations.SETTINGS_ROUTE,
            action = {
                navigateToSettings()
                closeDrawer()
            }
        )
        DrawerItem(
            painter = painterResource(id = R.drawable.ic_info_white),
            label = stringResource(id = R.string.about),
            isSelected = currentRoute == NewsDestinations.ABOUT_ROUTE,
            action = {
                navigateToAbout()
                closeDrawer()
            }
        )
    }
}

@Composable
private fun DrawerHeader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.header_height))
    ) {
        Image(
            modifier = modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.bg_side_nav),
            contentDescription = "image",
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.ic_side_nav_head),
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.author),
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(id = R.string.mail),
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun DrawerItem(
    painter: Painter,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tintColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    }

    NavigationDrawerItem(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin)),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = tintColor
            )
        },
        onClick = action,
        selected = isSelected,
        icon = {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = tintColor
            )
        }
    )
}

@Preview
@Composable
private fun DrawerHeaderPreview() {
    NewsTheme {
        DrawerHeader()
    }
}