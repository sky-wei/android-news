/*
 * Copyright (c) 2020 The sky Authors.
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

package com.sky.android.news.ui.setting

import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bumptech.glide.Glide
import com.sky.android.news.Constant
import com.sky.android.news.R
import com.sky.android.news.data.cache.impl.CacheManagerImpl

/**
 * Created by sky on 17-10-11.
 */

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        addPreferencesFromResource(R.xml.setting_preferences)

        // 添加事件监听
        findPreference<Preference>(
                Constant.Preference.CLEAR_NEWS_CACHE
        )?.onPreferenceClickListener = this
        findPreference<Preference>(
                Constant.Preference.CLEAR_IMAGE_CACHE
        )?.onPreferenceClickListener = this
    }

    override fun onPreferenceClick(preference: Preference): Boolean {

        when(preference.key) {
            Constant.Preference.CLEAR_NEWS_CACHE -> {
                // 清除新闻缓存
                CacheManagerImpl.getInstance(requireContext()).clear()
                Toast.makeText(activity, "清空缓存成功", Toast.LENGTH_SHORT).show()
            }
            Constant.Preference.CLEAR_IMAGE_CACHE -> {
                // 清除图片缓存
                Thread {
                    Glide.get(requireContext()).clearDiskCache()
                }.start()
                Toast.makeText(activity, "清空缓存成功", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}
