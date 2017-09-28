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
import com.sky.android.news.contract.StoryDetailsContract
import com.sky.android.news.data.model.StoryDetailsModel
import com.sky.android.news.data.source.ZhiHuDataSource

/**
 * Created by sky on 17-9-28.
 */
class StoryDetailsPresenter(val source: ZhiHuDataSource,
                            val view: StoryDetailsContract.View) : AbstractPresenter(), StoryDetailsContract.Presenter {

    override fun loadDetails(id: String) {

        ioToMain(source.getStory(id))
                .subscribe(DetailsSubscriber())
    }

    private inner class DetailsSubscriber : BaseSubscriber<StoryDetailsModel>() {

        override fun onError(msg: String, tr: Throwable): Boolean {
            // 加载失败
            Alog.e(msg, tr)
            view.cancelLoading()
            view.onLoadFailed("加载详情内容失败")
            return true
        }

        override fun onNext(model: StoryDetailsModel?) {

            if (model == null) {
                // 返回数据为空
                view.onLoadFailed("服务返回数据为空")
                return
            }

            // 加载成功
            view.cancelLoading()
            view.onLoadDetails(model)
        }
    }
}