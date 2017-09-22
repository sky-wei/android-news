package com.sky.android.news.data.news

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by sky on 17-9-21.
 */

data class HeadLine(@SerializedName("T1348647909107") val lineItems: List<LineItem>)

data class LineItem(val votecount: Int, val docid: String, val lmodify: String,
                    val url_3w: String, val source: String, val postid: String,
                    val priority: Int, val title: String, val replyCount: Int,
                    val ltitle: String, val subtitle: String, val digest: String,
                    val boardid: String, val imgsrc: String, val ptime: String,
                    val daynum: String)
