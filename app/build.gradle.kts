plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.sky.android.news"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sky.android.news"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Architecture Components
//    implementation(libs.room.runtime)
//    implementation(libs.room.ktx)
//    ksp(libs.room.compiler)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    // Hilt
    implementation(libs.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.coil)

//    implementation 'androidx.appcompat:appcompat:1.2.0'
//    implementation 'androidx.recyclerview:recyclerview:1.2.0'
//    implementation 'androidx.cardview:cardview:1.0.0'
//    implementation 'com.google.android.material:material:1.3.0'
//    implementation "androidx.preference:preference-ktx:1.1.1"
//    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
//    implementation 'androidx.vectordrawable:vectordrawable:1.1.0'
//    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
//    implementation("androidx.multidex:multidex:2.0.1")
//    implementation 'com.ogaclejapan.smarttablayout:library:2.0.0@aar'
//    implementation 'com.ogaclejapan.smarttablayout:utils-v4:2.0.0@aar'
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
//    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
//    implementation 'com.github.bumptech.glide:glide:4.11.0'
    implementation(libs.disklrucache)
//    implementation 'com.jaeger.statusbarutil:library:1.4.0'
//    implementation 'com.bigkoo:convenientbanner:2.0.5'
//    implementation 'com.yanzhenjie:permission:2.0.3'
//    implementation 'com.hi-dhl:binding:1.1.2'
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
//
//    testImplementation 'junit:junit:4.13.2'
//    kapt 'com.github.bumptech.glide:compiler:4.11.0'
//    implementation(project(":base"))
//    implementation(project(":core"))
}