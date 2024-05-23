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

import android.view.View;
import android.widget.TextView;

/**
 * Created by sky on 2020-11-28.
 */
public class ViewUtil {

    public static void setVisibility(View view, int visibility) {

        if (view == null || view.getVisibility() == visibility) return ;

        view.setVisibility(visibility);
    }

    public static void setVisibility(View view, boolean visibility) {
        setVisibility(view, visibility ? View.VISIBLE : View.GONE);
    }

    public static void setVisibility(int visibility, View... views) {

        if (views == null) return ;

        for (View view : views) {
            setVisibility(view, visibility);
        }
    }

    public static String getText(TextView textView) {
        return textView != null ? charSequenceToString(textView.getText()) : null;
    }

    public static String charSequenceToString(CharSequence charSequence) {
        return charSequence != null ? charSequence.toString() : null;
    }
}
