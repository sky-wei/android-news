pluginManagement {
    repositories {
        maven { url=uri("https://maven.aliyun.com/repository/public") }
        maven { url=uri("https://maven.aliyun.com/repository/central") }
        maven { url=uri("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url=uri("https://jitpack.io") }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url=uri("https://maven.aliyun.com/repository/public") }
        maven { url=uri("https://maven.aliyun.com/repository/central") }
        maven { url=uri("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url=uri("https://jitpack.io") }
        google()
        mavenCentral()
    }
}

rootProject.name = "android-news"
include(":app")

//file("library").listFiles()?.forEach {
//    if (it.name == "base") {
//        include(it.name)
//        project(":${it.name}").projectDir = it
//    }
//}
