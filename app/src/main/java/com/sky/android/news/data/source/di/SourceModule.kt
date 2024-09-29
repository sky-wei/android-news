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

package com.sky.android.news.data.source.di

import com.sky.android.news.data.source.INewsSource
import com.sky.android.news.data.source.IStorySource
import com.sky.android.news.data.source.local.NewsLocalSource
import com.sky.android.news.data.source.local.StoryLocalSource
import com.sky.android.news.data.source.remote.NewsRemoteSource
import com.sky.android.news.data.source.remote.StoryRemoteSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

/**
 * Created by sky on 2021-03-12.
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteSource


@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    @LocalSource
    abstract fun bindNewsLocalSource(source: NewsLocalSource): INewsSource

    @Binds
    @LocalSource
    abstract fun bindStoryLocalSource(source: StoryLocalSource): IStorySource

    @Binds
    @RemoteSource
    abstract fun bindNewsRemoteSource(source: NewsRemoteSource): INewsSource

    @Binds
    @RemoteSource
    abstract fun bindStoryRemoteSource(source: StoryRemoteSource): IStorySource
}