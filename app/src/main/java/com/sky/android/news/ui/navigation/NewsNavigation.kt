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

import androidx.navigation.NavHostController
import com.sky.android.news.ui.navigation.NewsScreens.ABOUT
import com.sky.android.news.ui.navigation.NewsScreens.NEWS
import com.sky.android.news.ui.navigation.NewsScreens.SETTINGS
import com.sky.android.news.ui.navigation.NewsScreens.SPLASH
import com.sky.android.news.ui.navigation.NewsScreens.STORY

private object NewsScreens {
    const val SPLASH = "splash"
    const val NEWS = "news"
    const val STORY = "story"
    const val SETTINGS = "settings"
    const val ABOUT = "about"
}

object NewsDestinations {
    const val SPLASH_ROUTE = SPLASH
    const val NEWS_ROUTE = NEWS
    const val STORY_ROUTE = STORY
    const val SETTINGS_ROUTE = SETTINGS
    const val ABOUT_ROUTE = ABOUT
}


class NewsNavigationActions(
    private val navController: NavHostController
) {
    fun navigateToSplash() {
        navController.navigate(SPLASH)
    }

    fun navigateToNews() {
        navController.navigate(NEWS)
    }

    fun navigateToStory() {
        navController.navigate(STORY)
    }

    fun navigateToSettings() {
        navController.navigate(SETTINGS)
    }

    fun navigateToAbout() {
        navController.navigate(ABOUT)
    }
}