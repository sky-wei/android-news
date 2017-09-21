package com.sky.android.news.data.source

import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.HeadLineModel
import rx.Observable

/**
 * Created by sky on 17-9-21.
 */
class NewsDataRepository(sourceFactory: NewsSourceFactory) : NewsDataSource {

    private val mLocal = sourceFactory.createLocalSource()
    private val mRemote = sourceFactory.createRemoteSource()

    override fun getCategory(): Observable<CategoryModel> {

        val localObservable = mLocal.getCategory()
        val remoteObservable = mRemote.getCategory()

        return Observable
                .concat(localObservable, remoteObservable)
                .takeFirst { model -> model != null }
    }

    override fun getHeadLine(tid: String, start: Int, end: Int): Observable<HeadLineModel> {

        val localObservable = mLocal.getHeadLine(tid, start, end)
        val remoteObservable = mRemote.getHeadLine(tid, start, end)

        return Observable
                .concat(localObservable, remoteObservable)
                .takeFirst { model -> model != null }
    }
}