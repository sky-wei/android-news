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

package com.sky.android.news.data.cache.impl

import android.content.Context
import com.google.gson.Gson
import com.jakewharton.disklrucache.DiskLruCache
import com.sky.android.common.util.Alog
import com.sky.android.common.util.FileUtil
import com.sky.android.common.util.MD5Util
import com.sky.android.news.BuildConfig
import com.sky.android.news.data.cache.CacheManager
import java.io.File
import java.io.IOException

/**
 * Created by sky on 17-9-21.
 */
class CacheManagerImpl private constructor(private val mContext: Context): CacheManager {

    private var mDiskLruCache: DiskLruCache? = null
    private var mGson: Gson = Gson()

    companion object {

        private val TAG = CacheManagerImpl::class.java.simpleName

        private const val MAX_SIZE = 1024 * 1024 * 20

        @Volatile
        private var instance: CacheManager? = null

        fun getInstance(context: Context): CacheManager {

            if (instance == null) {
                synchronized(CacheManagerImpl::class) {
                    if (instance == null) {
                        instance = CacheManagerImpl(context)
                    }
                }
            }
            return instance!!
        }
    }

    init {
        initCacheManager()
    }

    private fun initCacheManager() {

        val version = BuildConfig.VERSION_CODE

        val cacheDir = File(mContext.cacheDir, "net_cache")
        if (!cacheDir.exists()) FileUtil.createDir(cacheDir)

        mDiskLruCache = try {
            DiskLruCache.open(cacheDir, version, 1, MAX_SIZE.toLong())
        } catch (e: IOException) {
            Alog.e(TAG, "打开缓存目录异常", e)
            null
        }
    }

    @Synchronized
    override fun <T> get(key: String, tClass: Class<T>): T? {

        try {
            // 获取缓存信息
            val snapshot = get(key)

            if (snapshot != null) {
                // 返回相应的信息
                return mGson.fromJson(snapshot.getString(0), tClass)
            }
        } catch (e: Exception) {
            Alog.e(TAG, "获取信息失败", e)
        }
        return null
    }

    @Synchronized
    override fun <T> put(key: String, value: T): Boolean {

        var editor: DiskLruCache.Editor? = null

        try {
            // 获取编辑器
            editor = edit(key)

            if (editor != null) {
                // 保存数据
                editor.set(0, mGson.toJson(value))
                editor.commit()
                return true
            }
        } catch (e: Exception) {
            abortQuietly(editor)
            Alog.e(TAG, "保存分类信息出错！", e)
        } finally {
            flushQuietly()
        }
        return false
    }

    @Synchronized
    override fun remove(key: String): Boolean {

        if (!verifyCache()) return false

        try {
            // 删除数据
            mDiskLruCache!!.remove(key)
            return true
        } catch (e: IOException) {
            Alog.e(TAG, "移除数据失败", e)
        }
        return false
    }

    override fun clear() {

        if (!verifyCache()) return

        try {
            // 删除数据
            mDiskLruCache!!.delete()
            initCacheManager()
        } catch (e: IOException) {
            Alog.e(TAG, "删除数据失败", e)
        }
    }

    @Synchronized
    override fun close() {

        if (!verifyCache()) return

        try {
            mDiskLruCache!!.close()
        } catch (e: IOException) {
            Alog.e(TAG, "关闭缓存失败", e)
        }
    }

    override fun buildKey(value: String): String = MD5Util.md5sum(value)

    private fun verifyCache(): Boolean = mDiskLruCache != null

    @Throws(IOException::class)
    private operator fun get(key: String): DiskLruCache.Snapshot? =
            if (verifyCache()) mDiskLruCache!!.get(key) else null

    @Throws(IOException::class)
    private fun edit(key: String): DiskLruCache.Editor? =
            if (verifyCache()) mDiskLruCache!!.edit(key) else null

    @Throws(IOException::class)
    private fun abort(editor: DiskLruCache.Editor?) {
        editor?.abort()
    }

    private fun abortQuietly(editor: DiskLruCache.Editor?) {
        try {
            abort(editor)
        } catch (e: IOException) {
            Alog.e(TAG, "abortQuietly", e)
        }
    }

    @Throws(IOException::class)
    private fun flush() {
        if (verifyCache()) mDiskLruCache!!.flush()
    }

    private fun flushQuietly() {
        try {
            flush()
        } catch (e: IOException) {
            Alog.e(TAG, "flushQuietly", e)
        }
    }
}