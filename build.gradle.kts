buildscript {
    val agp_version by extra("7.2")
    val agp_version1 by extra("7.2.0")

    dependencies {
        val hiltVersion = "2.42"
        classpath ("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")

        val nav_version = "2.3.5"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version") //add here.

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("com.google.dagger.hilt.android") version "2.42" apply false

}