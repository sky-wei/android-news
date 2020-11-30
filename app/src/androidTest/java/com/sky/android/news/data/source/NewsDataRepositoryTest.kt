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

package com.sky.android.news.data.source

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.util.Log
import com.google.gson.Gson
import org.junit.After
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import rx.schedulers.Schedulers

/**
 * Created by sky on 17-9-22.
 */
class NewsDataRepositoryTest {

    private val TAG = "NewsDataRepositoryTest"

    lateinit var source: NewsDataSource

    @Before
    fun setUp() {

        val appContext = androidx.test.platform.app.InstrumentationRegistry.getTargetContext()
        source = NewsDataRepository(NewsSourceFactory(appContext))
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getCategory() {

        source.getCategory()
                .subscribe(
                        {
                            Assert.assertNotNull(it)
                            toString(it)
                        },
                        {
                            Assert.fail()
                            Log.e(TAG, "异常了", it)
                        }
                )
    }

    @Test
    fun getHeadLine() {

        source.getHeadLine("T1348647909107", 0, 20)
                .subscribe(
                        {
                            Assert.assertNotNull(it)
                            toString(it)
                        },
                        {
                            Assert.fail()
                            Log.e(TAG, "异常了", it)
                        }
                )
    }

    @Test
    fun getDetails() {

    }

    fun toString(any: Any?) {

        if (any == null) {
            Log.d(TAG, ">>> null")
            return
        }
        Log.d(TAG, ">>> ${Gson().toJson(any)}")
    }
}