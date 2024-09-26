/*
 * Copyright (c) 2017 The sky Authors.
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

package com.sky.android.news

/**
 * Created by sky on 17-9-21.
 */
object Constant {

    object Service {

        const val NEWS_URL = "https://c.m.163.com/"

        const val STORY_URL = "https://news-at.zhihu.com/"
    }

    object Category {

        const val NEWS = 0x01

        const val ZHI_HU = 0x02
    }

    object Preference {

        const val CLEAR_NEWS_CACHE = "clear_news_cache"

        const val CLEAR_IMAGE_CACHE = "clear_image_cache"
    }
}