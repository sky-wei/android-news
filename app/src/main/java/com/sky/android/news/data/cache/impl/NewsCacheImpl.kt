package com.sky.android.news.data.cache.impl

import com.sky.android.news.data.cache.CacheManager
import com.sky.android.news.data.cache.NewsCache
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.HeadLineModel
import rx.Observable
import rx.Subscriber

/**
 * Created by sky on 17-9-21.
 */
class NewsCacheImpl(private val mCacheManager: CacheManager) : NewsCache {

    private var mCategoryKey = mCacheManager.buildKey(
            NewsCacheImpl::class.java.name + ":getCategory()")

    override fun getCategory(): Observable<CategoryModel> {

        return Observable.unsafeCreate<CategoryModel> {
            subscriber -> handler(subscriber,
                mCacheManager.get(mCategoryKey, CategoryModel::class.java))
        }
    }

    override fun saveCategory(model: CategoryModel) {
        mCacheManager.put(mCategoryKey, model)
    }

    override fun getHeadLine(path: String): Observable<HeadLineModel> {

        return Observable.unsafeCreate<HeadLineModel> { subscriber ->
            val key = mCacheManager.buildKey(path)

            handler(subscriber, mCacheManager.get(key, HeadLineModel::class.java))
        }
    }

    override fun saveHeadLine(path: String, model: HeadLineModel) {

        val key = mCacheManager.buildKey(path)

        mCacheManager.put(key, model)
    }

    private fun <T> handler(subscriber: Subscriber<in T>, model: T) {

        try {
            // 处理下一步
            subscriber.onNext(model)
        } catch (e: Exception) {
            // 出错了
            subscriber.onError(e)
            return
        }
        // 完成
        subscriber.onCompleted()
    }
}