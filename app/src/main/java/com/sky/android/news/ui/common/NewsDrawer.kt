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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sky.android.news.R
import com.sky.android.news.ui.navigation.NewsDestinations
import com.sky.android.news.ui.navigation.NewsNavigationActions
import com.sky.android.news.ui.theme.primaryDarkColor
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
            AppDrawer(
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
private fun AppDrawer(
    currentRoute: String,
    navigateToNews: () -> Unit,
    navigateToStory: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAbout: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier,
        drawerShape = RectangleShape
    ) {
        DrawerHeader()
        DrawerButton(
            painter = painterResource(id = R.drawable.ic_video_library_white),
            label = stringResource(id = R.string.news),
            isSelected = currentRoute == NewsDestinations.NEWS_ROUTE,
            action = {
                navigateToNews()
                closeDrawer()
            }
        )
        DrawerButton(
            painter = painterResource(id = R.drawable.ic_video_library_white),
            label = stringResource(id = R.string.story),
            isSelected = currentRoute == NewsDestinations.STORY_ROUTE,
            action = {
                navigateToStory()
                closeDrawer()
            }
        )
        Divider()
        DrawerButton(
            painter = painterResource(id = R.drawable.ic_settings_applications_white),
            label = stringResource(id = R.string.setting),
            isSelected = currentRoute == NewsDestinations.SETTINGS_ROUTE,
            action = {
                navigateToSettings()
                closeDrawer()
            }
        )
        DrawerButton(
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
    Surface {

    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(primaryDarkColor)
            .height(dimensionResource(id = R.dimen.header_height))
            .padding(dimensionResource(id = R.dimen.header_padding))
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_no_fill),
            contentDescription = "Image",
            modifier = Modifier.width(dimensionResource(id = R.dimen.header_image_width))
        )
        Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
private fun DrawerButton(
    painter: Painter,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tintColor = if (isSelected) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
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

//    TextButton(
//        onClick = action,
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Start,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//
//            Spacer(
//                modifier = Modifier.width(16.dp)
//            )
//
//        }
//    }
}