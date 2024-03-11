plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs")
}

android {
    namespace = "com.noman.movie"
    compileSdk = 34

    packagingOptions {
        exclude("META-INF/LICENSE.md")
    }

    defaultConfig {
        applicationId = "com.noman.movie"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.google.ar:core:1.25.0")
    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    val ktxVersion = "1.8.0"
    implementation ("androidx.core:core-ktx:$ktxVersion")

    val lifecycleVersion = "2.5.1"
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    val roomVersion = "2.5.1"
    implementation ("androidx.room:room-runtime:$roomVersion")
    kapt ("androidx.room:room-compiler:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")

    val navVersion = "2.3.5"
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")

    val hiltVersion = "2.42"
    implementation ("com.google.dagger:hilt-android:$hiltVersion")
    kapt ("com.google.dagger:hilt-android-compiler:$hiltVersion")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")

    val retrofitVersion = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    val okhttpVersion = "4.7.2"
    implementation ("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation ("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

    val pagingVersion = "3.1.0"
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    implementation("androidx.paging:paging-common-ktx:$pagingVersion")

    val glideVersion = "4.13.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")


    /**
     * Testing Dependencies
     */

    // Hilt for testing
    val hilt_testing_version = "2.42"
    testImplementation ("com.google.dagger:hilt-android-testing:$hilt_testing_version")
    kaptTest ("com.google.dagger:hilt-android-compiler:$hilt_testing_version")

    // Hamcrest
    testImplementation ("org.hamcrest:hamcrest-all:1.3")

    //For runBlockingTest, CoroutineDispatcher etc.
    val coroutines_test_version = "1.4.2"
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_test_version")

    // Junit
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")

    // AndroidX Test - JVM testing
    testImplementation ("androidx.test.ext:junit-ktx:1.1.3")
    testImplementation ("androidx.test:core-ktx:1.4.0")
    testImplementation ("org.robolectric:robolectric:4.5.1")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")

}