/*
 * Copyright (c) 2018 The sky Authors.
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

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

/**
 * Created by sky on 16-12-3.
 */
public class BroadcastUtil {

    public static void sendBroadcast(@NonNull Context context, @NonNull Intent intent) {
        context.sendBroadcast(intent);
    }

    public static void sendBroadcast(@NonNull Instrumentation instrumentation, @NonNull Intent intent) {
        sendBroadcast(instrumentation.getTargetContext(), intent);
    }

    public static void sendBroadcast(@NonNull Instrumentation instrumentation, @NonNull String action) {
        sendBroadcast(instrumentation, new Intent(action));
    }

    public static void sendBroadcast(@NonNull Context context, @NonNull String action) {
        sendBroadcast(context, new Intent(action));
    }
}
