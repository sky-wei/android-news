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

package com.sky.android.news.ext

import android.content.Context
import com.sky.android.news.R
import com.sky.android.news.data.exception.RemoteSourceException

/**
 * Created by sky on 11/25/24.
 */
fun RemoteSourceException.getError(context: Context): String {
    return when (messageResource) {
        is Int -> context.getString(messageResource)
        is String -> messageResource
        else -> context.getString(R.string.error_unexpected_message)
    }
}