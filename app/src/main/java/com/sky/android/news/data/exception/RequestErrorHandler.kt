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

package com.sky.android.news.data.exception

import com.sky.android.news.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by sky on 11/25/24.
 */
object RequestErrorHandler {

    private const val HTTP_CODE_CLIENT_START = 400
    private const val HTTP_CODE_CLIENT_END = 499
    private const val HTTP_CODE_SERVER_START = 500
    private const val HTTP_CODE_SERVER_END = 599

    fun getRequestError(throwable: Throwable): RemoteSourceException {
        return when (throwable) {
            is HttpException -> {
                handleHttpException(throwable)
            }
            is SocketTimeoutException -> {
                RemoteSourceException.Timeout(R.string.error_timeout_message)
            }
            is IOException -> {
                RemoteSourceException.Connection(R.string.error_network)
            }
            else -> {
                RemoteSourceException.Unexpected(R.string.error_unexpected_message)
            }
        }
    }

    private fun handleHttpException(httpException: HttpException): RemoteSourceException {
        return when (httpException.code()) {
            in HTTP_CODE_CLIENT_START..HTTP_CODE_CLIENT_END -> {
                RemoteSourceException.Client(R.string.error_client_unexpected_message)
            }

            in HTTP_CODE_SERVER_START..HTTP_CODE_SERVER_END -> {
                RemoteSourceException.Server(R.string.error_server_unexpected_message)
            }

            else -> {
                RemoteSourceException.Unexpected(R.string.error_unexpected_message)
            }
        }
    }
}