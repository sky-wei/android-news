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

package com.sky.android.common.util;

import android.os.Build;
import android.os.Process;
import android.os.UserHandle;

import androidx.annotation.RequiresApi;

/**
 * Created by sky on 2020-11-28.
 */
public class UserUtil {

    private UserUtil() {

    }

    /**
     * 获取系统用户id
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getIdentifier() {
        return (int) ReflectUtils.invokeQuietly(Process.myUserHandle(), "getIdentifier");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static UserHandle of(int identifier) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 7.0以上使用这个方法
            return (UserHandle) ReflectUtils.invokeQuietly(
                    UserHandle.class, "of", new Class[]{int.class}, new Object[]{identifier});
        }
        // 7.0以下的使用这个方法
        return (UserHandle) ReflectUtils.newInstance(
                UserHandle.class, new Class[]{int.class}, new Object[]{identifier});
    }
}
