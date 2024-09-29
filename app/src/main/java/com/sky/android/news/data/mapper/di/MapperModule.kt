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

package com.sky.android.news.data.mapper.di

import com.sky.android.news.data.mapper.news.DetailsMapper
import com.sky.android.news.data.mapper.news.HeadLineMapper
import com.sky.android.news.data.mapper.story.StoryDetailsMapper
import com.sky.android.news.data.mapper.story.StoryListMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    abstract fun bindHeadLineMapper(mapper: HeadLineMapper): HeadLineMapper

    @Binds
    abstract fun bindDetailsMapper(mapper: DetailsMapper): DetailsMapper

    @Binds
    abstract fun bindStoryListMapper(mapper: StoryListMapper): StoryListMapper

    @Binds
    abstract fun bindStoryDetailsMapper(mapper: StoryDetailsMapper): StoryDetailsMapper
}