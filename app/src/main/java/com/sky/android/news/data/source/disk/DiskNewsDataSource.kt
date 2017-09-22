package com.sky.android.news.data.source.disk

import android.content.Context
import com.sky.android.news.data.cache.NewsCache
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getHeadLine(tid: String, start: Int, end: Int): Observable<HeadLineModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDetails(docId: String): Observable<DetailsModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}