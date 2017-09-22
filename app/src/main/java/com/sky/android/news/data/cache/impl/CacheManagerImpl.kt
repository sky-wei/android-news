package com.sky.android.news.data.cache.impl

import android.content.Context
import android.os.Environment
import com.google.gson.Gson
import com.jakewharton.disklrucache.DiskLruCache
import com.sky.android.common.utils.Alog
import com.sky.android.common.utils.FileUtils
import com.sky.android.common.utils.MD5Utils
import com.sky.android.news.BuildConfig
import com.sky.android.news.data.cache.CacheManager
import java.io.File
import java.io.IOException

/**
 * Created by sky on 17-9-21.
 */
class CacheManagerImpl private constructor(mContext: Context): CacheManager {

    private val TAG = CacheManagerImpl::class.java.simpleName

    private val MAX_SIZE = 1024 * 1024 * 20

    private var mDiskLruCache: DiskLruCache? = null
    private var mGson: Gson = Gson()

    companion object {

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
        val version = BuildConfig.VERSION_CODE

        val cacheDir = File(mContext.cacheDir, "net_cache")
        if (!cacheDir.exists()) FileUtils.createDir(cacheDir)

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

    @Synchronized
    override fun close() {

        if (!verifyCache()) return

        try {
            mDiskLruCache!!.close()
        } catch (e: IOException) {
            Alog.e(TAG, "关闭缓存失败", e)
        }
    }

    override fun buildKey(value: String): String {
        return MD5Utils.md5sum(value)
    }

    private fun verifyCache(): Boolean {
        return mDiskLruCache != null
    }

    @Throws(IOException::class)
    private operator fun get(key: String): DiskLruCache.Snapshot? {
        return if (verifyCache()) mDiskLruCache!!.get(key) else null
    }

    @Throws(IOException::class)
    private fun edit(key: String): DiskLruCache.Editor? {
        return if (verifyCache()) mDiskLruCache!!.edit(key) else null
    }

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