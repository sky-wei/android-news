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

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {

    data object News: Screen("news")

    data object NewsDetail: Screen(
        route = "news/{tid}",
        navArguments = listOf(navArgument("tid") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(tid: String) = "news/$tid"
    }

    data object Story: Screen("story")

    data object StoryDetail: Screen(
        route = "story/{id}",
        navArguments = listOf(navArgument("id") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(id: String) = "story/$id"
    }

    data object Setting: Screen("setting")

    data object About: Screen("about")
}