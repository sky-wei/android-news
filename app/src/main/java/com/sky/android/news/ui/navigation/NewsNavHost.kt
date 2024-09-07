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

package com.sky.android.news.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sky.android.news.ui.about.AboutScreen
import com.sky.android.news.ui.common.AppModalDrawer
import com.sky.android.news.ui.news.NewsScreen
import com.sky.android.news.ui.settings.SettingsScreen
import com.sky.android.news.ui.splash.SplashScreen
import com.sky.android.news.ui.story.StoryScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NewsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = Screen.News.route,
    navActions: NewsNavigationActions = remember(navController) {
        NewsNavigationActions(navController)
    }
) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            Screen.Splash.route
        ) {
            SplashScreen(navController = navController)
        }
        composable(
            Screen.News.route
        ) {
            AppModalDrawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions = navActions
            ) {
                NewsScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } }
                )
            }
        }
        composable(
            Screen.Story.route
        ) {
            AppModalDrawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions = navActions
            ) {
                StoryScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } }
                )
            }
        }
        composable(
            Screen.Setting.route
        ) {
            SettingsScreen(
                onBack = { coroutineScope.launch { navController.popBackStack() } }
            )
        }
        composable(
            Screen.About.route
        ) {
            AboutScreen(
                onBack = { coroutineScope.launch { navController.popBackStack() } }
            )
        }
    }
}