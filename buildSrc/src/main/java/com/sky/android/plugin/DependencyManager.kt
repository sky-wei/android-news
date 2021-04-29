/*
 * Copyright (c) 2021 The sky Authors.
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

package com.sky.android.plugin

object Versions {

    const val appcompat = "1.2.0"

    const val recyclerview = "1.1.0"

    const val cardview = "1.0.0"

    const val material = "1.2.1"

    const val preference = "1.1.1"
}

object AndroidX {

    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"

    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"

    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"

    const val preferenceKtx = "androidx.preference:preference-ktx:${Versions.preference}"

    const val legacySupportV4 = "androidx.legacy:legacy-support-v4:1.0.0"

    const val vectordrawable = "androidx.vectordrawable:vectordrawable:1.1.0"

    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"

    const val multidex = "androidx.multidex:multidex:2.0.1"
}

object Android {

    const val material = "com.google.android.material:material:${Versions.material}"
}

object SmartTabLayout {

    const val library = "com.ogaclejapan.smarttablayout:library:2.0.0@aar"

    const val utilsV4 = "com.ogaclejapan.smarttablayout:utils-v4:2.0.0@aar"
}

object Retrofit2 {

    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"

    const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"

    const val kotlinCoroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
}