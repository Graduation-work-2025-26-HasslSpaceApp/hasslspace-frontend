import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "ru.hse.app.androidApp"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        targetSdk = 36

        applicationId = "ru.hse.app.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-metadata-jvm:2.3.0")

    implementation(project(":sharedUI"))
    implementation(libs.androidx.activityCompose)

    //TODO перенести в libs
    implementation(libs.androidx.material.icons.extended.android)

    implementation("androidx.appcompat:appcompat:1.7.1")

    // Color Picker
    implementation("com.github.skydoves:colorpicker-compose:1.1.3")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.4.0")

    // Hilt and kapt
    implementation("com.google.dagger:hilt-android:2.57.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
    kapt("org.jetbrains.kotlin:kotlin-metadata-jvm:2.3.0")
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")


    //Coil
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.palette.ktx)

    //Image Cropper
    implementation(libs.ucrop)
}
