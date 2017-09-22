package com.sky.android.news.data.news

import java.io.Serializable

/**
 * Created by sky on 17-9-21.
 */

data class HeadLine(var lineItems: List<LineItem>) : Serializable

data class LineItem(val votecount: Int, val docid: String, val lmodify: String,
                    val url_3w: String, val source: String, val postid: String,
                    val priority: Int, val title: String, val replyCount: Int,
                    val ltitle: String, val subtitle: String, val digest: String,
                    val boardid: String, val imgsrc: String, val ptime: String,
                    val daynum: String) : Serializable
