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

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Method;

/**
 * Created by sky on 2020-11-28.
 */
public class ScaleViewUtil {

    private static int width = 0;
    private static int height = 0;

    private static boolean mInit = false;
    /**
     * 同分辨率不再计算
     */
    private static boolean noOpt = false;

    private static final int DEFINE_WIDTH = 1280;
    private static final int DEFINE_Height = 720;

    public static boolean isNoOpt() {
        return noOpt;
    }

    public static void setNoOpt(boolean noOpt) {
        ScaleViewUtil.noOpt = noOpt;
    }

    private static void init(int width, int height) {
        ScaleViewUtil.width = width;
        ScaleViewUtil.height = height;

        Alog.d("windows", "w: " + width + ", h:" + height);

        if (DEFINE_WIDTH == width && DEFINE_Height == height) {
            setNoOpt(true);
        }
    }

    /**
     * 初始化获取设备的真是宽高,只计算一次
     * @param context
     */
    public static void init(Context context) {

        if (mInit) return ;

        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getHeightPixels(context);
        if(widthPixels > heightPixels) {
            init(widthPixels, heightPixels);
        } else {
            init(heightPixels, widthPixels);
        }
        mInit = true;
    }

    /**
     * 获取屏幕宽高信息
     * @param context
     * @return
     */
    public static Point getScreenPixels(Context context) {

        if (!mInit) {
            init(context);
        }

        return new Point(width, height);
    }

    private static int getHeightPixels(Context context) {
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        Display display = windowManager.getDefaultDisplay();

        int rawHeight;
        try {
            rawHeight = (Integer) Display.class.getDeclaredMethod("getRawHeight", (Class[]) null).invoke(display);
        } catch (Exception e) {
            int realHeight;
            try {
                realHeight = (Integer) Display.class.getDeclaredMethod("getRealHeight", (Class[]) null).invoke(display);
            } catch (Exception e1) {
                try {
                    Method method = Display.class.getDeclaredMethod("getRealSize", Point.class);
                    Point point = new Point();
                    method.invoke(display, point);
                    if(point.y > heightPixels) {
                        return point.y;
                    }

                    return heightPixels;
                } catch (Exception e2) {
                    return heightPixels;
                }
            }

            if(realHeight > heightPixels) {
                return realHeight;
            }

            return heightPixels;
        }

        int height = rawHeight;
        if(rawHeight <= heightPixels) {
            height = heightPixels;
        }

        return height;
    }

    /**
     * 缩放View
     * @param view
     */
    public static void scaleView(View view) {
        if(!isNoOpt() && view != null) {
            resetViewSize(view);
            if(view instanceof ViewGroup) {
                scaleView((ViewGroup) view);
            }
        }
    }

    /**
     * 缩放View
     * @param activity
     */
    public static void scaleView(Activity activity) {
        if(!isNoOpt() && activity != null) {
            scaleView(activity.getWindow().getDecorView());
        }
    }

    private static void scaleView(ViewGroup viewGroup) {
        for(int childId = -1 + viewGroup.getChildCount(); childId >= 0; --childId) {
            scaleView(viewGroup.getChildAt(childId));
        }
    }

    private static void resetViewSize(View view) {
        if(view == null) {
            Alog.e("ScaleView", "child is null");
        } else {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams)layoutParams).leftMargin = resetWidth(((ViewGroup.MarginLayoutParams) layoutParams).leftMargin);
                ((ViewGroup.MarginLayoutParams)layoutParams).rightMargin = resetWidth(((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                ((ViewGroup.MarginLayoutParams)layoutParams).topMargin = resetHeight(((ViewGroup.MarginLayoutParams) layoutParams).topMargin);
                ((ViewGroup.MarginLayoutParams)layoutParams).bottomMargin = resetHeight(((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
            }

            if(layoutParams != null) {
                if(layoutParams.height != -1 && layoutParams.height != -2) {
                    layoutParams.height = resetHeight(layoutParams.height);
                }

                if(layoutParams.width != -1 && layoutParams.width != -2) {
                    layoutParams.width = resetWidth(layoutParams.width);
                }
            }

            if(layoutParams != null) {
                view.setLayoutParams(layoutParams);
            }

            view.setPadding(resetWidth(view.getPaddingLeft()), resetHeight(view.getPaddingTop()), resetWidth(view.getPaddingRight()), resetHeight(view.getPaddingBottom()));
            if(view instanceof TextView) {
                ((TextView)view).setTextSize(0, resetTextSize(((TextView) view).getTextSize()));
            }

            // 暂时不需要
//            if (view instanceof GridView) {
//                GridView gridView = (GridView)view;
//
//                Object mHorizontalSpacing = ReflectUtils.getFieldValueQuietly(GridView.class, "mHorizontalSpacing", gridView);
//                if (mHorizontalSpacing != null) gridView.setHorizontalSpacing(resetWidth((Integer)mHorizontalSpacing));
//
//                Object mVerticalSpacing = ReflectUtils.getFieldValueQuietly(GridView.class, "mVerticalSpacing", gridView);
//                if (mVerticalSpacing != null) gridView.setVerticalSpacing(resetHeight((Integer)mVerticalSpacing));
//            }
        }

    }

    /**
     * 重新计算TextSize
     * @param textSize
     * @return
     */
    public static float resetTextSize(float textSize) {
        if (isNoOpt()) {
            return textSize;
        }
        return resetHeight(textSize);
    }

    private static float resetWidth(float w) {
        return w * (float) width / DEFINE_WIDTH;
    }

    /**
     * 重新计算width
     * @param width
     * @return
     */
    public static int resetWidth(int width) {
        if (noOpt) {
            return width;
        }
        return (int)resetWidth((float)width);
    }

    /**
     * 重新计算height
     * @param height
     * @return
     */
    public static int resetHeight(int height) {
        if (noOpt) {
            return height;
        }
        return (int)resetHeight((float)height);
    }

    private static float resetHeight(float h) {
        return h * (float) height / DEFINE_Height;
    }
}