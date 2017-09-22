package com.sky.android.news.data.source.cloud

import com.sky.android.news.Constant
import com.sky.android.news.data.cache.NewsCache
import com.sky.android.news.data.mapper.MapperFactory
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.model.DetailsModel
import com.sky.android.news.data.model.HeadLineModel
import com.sky.android.news.data.service.NewsService
import com.sky.android.news.data.source.NewsDataSource
import rx.Observable

/**
 * Created by sky on 17-9-21.
 */
class CloudNewsDataSource(private val mCache: NewsCache) : CloudDataSource(), NewsDataSource {

    override fun getCategory(): Observable<CategoryModel> {
        // 服务器没有这个功能
        return Observable.unsafeCreate<CategoryModel> {
            try {
                it.onNext(null)
                it.onCompleted()
            } catch (tr: Throwable) {
                it.onError(tr)
            }
        }
    }

    override fun getHeadLine(tid: String, start: Int, end: Int): Observable<HeadLineModel> {

        val service = buildVideoService()

        return service.getHeadLine(tid, start, end)
                .map { it -> MapperFactory.createHeadLineMapper().transform(it) }
    }

    override fun getDetails(docId: String): Observable<DetailsModel> {

        val service = buildVideoService()

        return service.getDetails(docId)
                .map { it -> MapperFactory.createDetailsMapper().transform(it) }
    }

    private fun buildVideoService(): NewsService {
        return buildService(NewsService::class.java, Constant.Service.BASE_URL)
    }
}