import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

val serverUrl: String by extra {
    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { properties.load(it) }
    }
    val value = properties.getProperty("SERVER_URL", "")
    if (value.isEmpty()) {
        throw InvalidUserDataException("Server URL is not provided")
    }
    value
}

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

        buildConfigField("String", "SERVER_URL", "\"$serverUrl\"")
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    // Jackson
    implementation("com.squareup.retrofit2:converter-jackson:3.0.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.3")

    //Logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Kotlinx Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
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

    //Easy Permissions
    implementation("pub.devrel:easypermissions:3.0.0")


    // emoji
    val emoji2Version = "1.6.0"

    implementation("androidx.emoji2:emoji2-emojipicker:1.6.0")
    implementation("androidx.emoji2:emoji2:$emoji2Version")
    implementation("androidx.emoji2:emoji2-views:$emoji2Version")
    implementation("androidx.emoji2:emoji2-views-helper:$emoji2Version")

    // LiveKit
    implementation(libs.livekit.lib)
    implementation(libs.livekit.components)
    // Runtime permissions for Compose
    implementation(libs.accompanist.permissions)

    // ConstraintLayout for call layout
    implementation(libs.constraintlayout.compose)


}
