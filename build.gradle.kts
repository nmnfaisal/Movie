buildscript {
    val agp_version by extra("7.2")
    val agp_version1 by extra("7.2.0")

    dependencies {
        val hiltVersion = "2.42"
        classpath ("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}