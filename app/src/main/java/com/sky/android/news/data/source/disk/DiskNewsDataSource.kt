package com.sky.android.news.data.source.disk

import android.content.Context
import com.sky.android.news.data.cache.NewsCache
import com.sky.android.news.data.model.CategoryItemModel
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.source.NewsDataSource
import rx.Observable

/**
 * Created by sky on 17-9-21.
 */
class DiskNewsDataSource(private val mContext: Context, private val mCache: NewsCache) : NewsDataSource {

    override fun getCategory(): Observable<CategoryModel> {
        return Observable.unsafeCreate {
            try {
                it.onNext(newCategory())
                it.onCompleted()
            } catch (tr: Throwable) {
                it.onError(tr)
            }
        }
    }

    override fun getHeadLine(tid: String, start: Int, end: Int): Observable<HeadLineModel> {
        return Observable.unsafeCreate {
            try {
                it.onNext(null)
                it.onCompleted()
            } catch (tr: Throwable) {
                it.onError(tr)
            }
        }
    }

    override fun getDetails(docId: String): Observable<DetailsModel> {
        return Observable.unsafeCreate {
            try {
                it.onNext(null)
                it.onCompleted()
            } catch (tr: Throwable) {
                it.onError(tr)
            }
        }
    }

    /**
     * 使用本地分类
     */
    private fun newCategory(): CategoryModel {

        val itemModes = listOf(
                CategoryItemModel("头条", "T1348647909107"),
                CategoryItemModel("科技", "T1348649580692"),
                CategoryItemModel("要闻", "T1467284926140"),
                CategoryItemModel("社会", "T1348648037603"))

        return CategoryModel(itemModes)
    }
}