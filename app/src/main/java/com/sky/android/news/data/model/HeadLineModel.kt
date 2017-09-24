package com.sky.android.news.data.model

import java.io.Serializable

/**
 * Created by sky on 17-9-21.
 */
data class HeadLineModel(var lineItems: List<LineItemModel>) : Serializable

data class LineItemModel(val voteCount: Int, val docId: String, val lModify: String,
                         val url3w: String, val source: String, val posTid: String,
                         val priority: Int, val title: String, val replyCount: Int,
                         val lTitle: String, val subtitle: String, val digest: String,
                         val boardId: String, val imgSrc: String, val pTime: String,
                         val dayNum: String, val template: String /** 特殊的字段，用于区分不支持的新闻 */) : Serializable