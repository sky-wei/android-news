package com.sky.android.news.ui.helper

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.sky.android.common.utils.DisplayUtils

/**
 * Created by sky on 17-9-22.
 */
class RecyclerHelper(private val refreshLayout: SwipeRefreshLayout,
                     private val recyclerView: RecyclerView, val onCallback: OnCallback)
    : RecyclerView.OnScrollListener(), SwipeRefreshLayout.OnRefreshListener {

    var mLoadMore: Boolean = false

    init {
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(this)
    }

    fun setLoadMore(loadMore: Boolean) {
        mLoadMore = loadMore
    }

    override fun onRefresh() {
        onCallback.onRefresh()
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (!mLoadMore) return

        if (newState == RecyclerView.SCROLL_STATE_IDLE
                && !refreshLayout.isRefreshing
                && isSlideToBottom(recyclerView)) {

            // 加载更多
            refreshLayout.isRefreshing = true
            onCallback.onLoadMore()
        }
    }

    fun isRefreshing(): Boolean {
        return refreshLayout.isRefreshing
    }

    fun forceRefreshing() {

        if (isRefreshing()) return

        // 显示加载进度
        refreshLayout.setProgressViewOffset(false, 0,
                DisplayUtils.dip2px(refreshLayout.context, 60f))
        refreshLayout.isRefreshing = true
    }

    fun cancelRefreshing() {

        if (!isRefreshing()) return

        refreshLayout.isRefreshing = false
    }

    private fun isSlideToBottom(recyclerView: RecyclerView): Boolean {
        return recyclerView.computeVerticalScrollExtent() +
                recyclerView.computeVerticalScrollOffset() >=
                recyclerView.computeVerticalScrollRange()
    }

    interface OnCallback {

        fun onRefresh()

        fun onLoadMore()
    }
}