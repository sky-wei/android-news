/*
 * Copyright (c) 2017 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.android.news.presenter

import com.sky.android.common.utils.Alog
import com.sky.android.news.base.BaseSubscriber
import com.sky.android.news.contract.CategoryContract
import com.sky.android.news.data.model.CategoryModel
import com.sky.android.news.data.source.NewsDataSource

/**
 * Created by sky on 17-9-22.
 */
class CategoryPresenter(val source: NewsDataSource,
                        val view: CategoryContract.View) : AbstractPresenter(), CategoryContract.Presenter {

    override fun loadCategory() {

        // 加载类别
        ioToMain(source.getCategory())
                .subscribe(CategorySubscriber())
    }

    private inner class CategorySubscriber : BaseSubscriber<CategoryModel>() {

        override fun onError(msg: String, tr: Throwable): Boolean {
            // 加载失败
            Alog.e(msg, tr)
            view.onLoadFailed("加载分类列表失败")
            return true
        }

        override fun onNext(model: CategoryModel) {

            // 加载成功
            view.onLoadCategory(model)
        }
    }
}