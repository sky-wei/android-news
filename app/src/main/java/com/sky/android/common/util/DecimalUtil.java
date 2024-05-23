package com.sky.android.common.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by sky on 11/28/20.
 */
public class DecimalUtil {

    @SuppressLint("StaticFieldLeak")
    private static volatile DecimalUtil singleton = null;

    private final DecimalFormat mDecimalFormat;

    private DecimalUtil(Builder builder) {
        mDecimalFormat = new DecimalFormat(builder.mPattern);
    }

    public String decimalFormat(Object obj) {
        return mDecimalFormat.format(obj);
    }

    public String decimalFormat(double number) {
        return mDecimalFormat.format(number);
    }

    public String decimalFormat(long number) {
        return mDecimalFormat.format(number);
    }

    public Number decimalParse(String source) throws ParseException {
        return mDecimalFormat.parse(source);
    }


    @NonNull
    public static DecimalUtil getInstance() {
        if (singleton == null) {
            synchronized (DecimalFormat.class) {
                if (singleton == null) {
                    singleton = new Builder().build();
                }
            }
        }
        return singleton;
    }

    public static void setSingletonInstance(@NonNull DecimalUtil decimalUtil) {
        synchronized (DecimalFormat.class) {
            if (singleton != null) {
                throw new IllegalStateException("Singleton instance already exists.");
            }
            singleton = decimalUtil;
        }
    }

    public static String format(long number) {
        return getInstance().decimalFormat(number);
    }

    public static String format(double number) {
        return getInstance().decimalFormat(number);
    }

    public static Number parse(@NonNull String source) throws ParseException {
        return getInstance().decimalParse(source);
    }

    public static class Builder {

        private String mPattern;

        public Builder setPattern(String pattern) {
            mPattern = pattern;
            return this;
        }

        public DecimalUtil build() {

            if (TextUtils.isEmpty(mPattern)) {
                mPattern = "0.00";
            }
            return new DecimalUtil(this);
        }
    }
}
