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

package com.sky.android.news.ui.helper

import java.util.*

/**
 * Created by sky on 17-9-22.
 */
class PageHelper<T> {

    companion object {

        const val PAGE_SIZE = 20
    }

    private var mCurPage: Int = 0
    private var mTotal: Int = 0
    private var mTotalPage: Int = 0
    private var mData = ArrayList<T>()
    var notFixed: Boolean = false

    constructor()

    constructor(data: List<T>) {
        mData.addAll(data)
    }

    fun setData(data: List<T>) {
        setData(data.size, data)
    }

    fun setData(total: Int, data: List<T>) {

        reset()
        mTotal = total

        if (mTotal > PAGE_SIZE) {
            mTotalPage = mTotal / PAGE_SIZE
            if (mTotal % PAGE_SIZE >= 1) mTotalPage++
        } else {
            mTotalPage = 1
        }

        if (!data.isEmpty()) {
            mData.addAll(data)
        }
    }

    fun getData(): List<T> = mData

    fun getDataItem(index: Int): T = mData[index]

    fun appendData(data: List<T>): Boolean {

        if (mData.isEmpty()
                || !isNextPage()) {
            return false
        }

        mCurPage++
        return mData.addAll(data)
    }

    fun isNextPage(): Boolean {

        if (notFixed) return true

        return mCurPage + 1 < mTotalPage
    }

    fun getCurPage(): Int = mCurPage

    private fun reset() {

        mCurPage = 0
        mTotal = 0
        mTotalPage = 0
        mData.clear()
    }
}