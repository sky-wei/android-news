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

package com.sky.android.news.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sky.android.news.ui.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by sky on 10/21/24.
 */

@Composable
fun rememberNewsAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
): NewsAppState {
    return remember(
        coroutineScope,
        navController
    ) {
        NewsAppState(
            coroutineScope = coroutineScope,
            navController = navController,
            drawerState = drawerState
        )
    }
}


@Stable
class NewsAppState(
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
    val drawerState: DrawerState
) {

    val currentRoute: String?
        @Composable get() = currentDestination?.route

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigateToNews() {
        navController.navigate(Screen.News.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToNewsDetail(tid: String) {
        navController.navigate(Screen.NewsDetail.createRoute(tid))
    }

    fun navigateToStory() {
        navController.navigate(Screen.Story.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToStoryDetail(id: String) {
        navController.navigate(Screen.StoryDetail.createRoute(id))
    }

    fun navigateToSettings() {
        navController.navigate(Screen.Setting.route)
    }

    fun navigateToAbout() {
        navController.navigate(Screen.About.route)
    }

    fun openDrawer() {
        coroutineScope.launch { drawerState.open() }
    }

    fun closeDrawer() {
        coroutineScope.launch { drawerState.close() }
    }

    fun popBack() {
        coroutineScope.launch { navController.popBackStack() }
    }
}