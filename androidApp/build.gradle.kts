import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
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
    implementation(project(":sharedUI"))
    implementation(libs.androidx.activityCompose)

    //TODO перенести в libs
    implementation(libs.androidx.material.icons.extended.android)

    implementation("androidx.appcompat:appcompat:1.7.1")

    // Color Picker
    implementation("com.github.skydoves:colorpicker-compose:1.1.3")
    //Coil
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.palette.ktx)

    //Image Cropper
    implementation(libs.ucrop)
}
