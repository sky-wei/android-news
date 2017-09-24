package com.sky.android.news.module

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule


/**
 * Created by sky on 17-9-23.
 */
@GlideModule
class VAppGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        builder.setMemoryCache(LruResourceCache(10 * 1024 * 1024))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, 500 * 1024 * 1024))
    }
}