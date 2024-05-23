package com.sky.android.common.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sky on 11/28/20.
 */
public class TimeUtil {

    @SuppressLint("StaticFieldLeak")
    private static volatile TimeUtil singleton = null;

    private final SimpleDateFormat mDateFormat;

    @SuppressLint("SimpleDateFormat")
    private TimeUtil(Builder builder) {
        mDateFormat = new SimpleDateFormat(builder.mPattern);
    }

    public String dateFormat(@NonNull Object obj) {
        return mDateFormat.format(obj);
    }

    public Date dateParse(@NonNull String source) throws ParseException {
        return mDateFormat.parse(source);
    }

    @NonNull
    public static TimeUtil getInstance() {
        if (singleton == null) {
            synchronized (TimeUtil.class) {
                if (singleton == null) {
                    singleton = new Builder().build();
                }
            }
        }
        return singleton;
    }

    public static void setSingletonInstance(@NonNull TimeUtil timeUtil) {
        synchronized (TimeUtil.class) {
            if (singleton != null) {
                throw new IllegalStateException("Singleton instance already exists.");
            }
            singleton = timeUtil;
        }
    }

    public static String format(long date) {
        return getInstance().dateFormat(date);
    }

    public static String format(@NonNull Date date) {
        return getInstance().dateFormat(date);
    }

    public static Date parse(@NonNull String source) throws ParseException {
        return getInstance().dateParse(source);
    }

    public static class Builder {

        private String mPattern;

        public Builder setPattern(String pattern) {
            mPattern = pattern;
            return this;
        }

        public TimeUtil build() {

            if (TextUtils.isEmpty(mPattern)) {
                mPattern = "yyyy-MM-dd HH:mm";
            }
            return new TimeUtil(this);
        }
    }
}
