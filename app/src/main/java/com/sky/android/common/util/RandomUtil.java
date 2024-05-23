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

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by sky on 2020-11-28.
 */
public class RandomUtil {

    private final static Random RANDOM =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                    ? ThreadLocalRandom.current() : new Random();

    private RandomUtil() {
    }

    /**
     * 去数组随机下标
     * @param size > 0
     * @return
     */
    public static int randomIndex(int size) {
        return RANDOM.nextInt(size);
    }

    public static int random(int start, int end) {
        return random(end - start) + start;
    }

    public static int random(int value) {
        return RANDOM.nextInt(value);
    }

    public static <T> T random(List<T> list) {
        return CollectionUtil.isEmpty(list) ? null : list.get(randomIndex(list.size()));
    }
}
