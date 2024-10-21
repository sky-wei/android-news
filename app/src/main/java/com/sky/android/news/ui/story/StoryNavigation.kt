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

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sky.android.news.ui.NewsAppState
import com.sky.android.news.ui.common.AppModalDrawer
import com.sky.android.news.ui.navigation.Screen

/**
 * Created by sky on 10/21/24.
 */

fun NavGraphBuilder.storyScreen(
    appState: NewsAppState,
    currentRoute: String
) {
    composable(
        Screen.Story.route
    ) {
        AppModalDrawer(
            appState = appState,
            currentRoute = currentRoute
        ) {
            StoryScreen(
                openDrawer = appState::openDrawer
            )
        }
    }
}