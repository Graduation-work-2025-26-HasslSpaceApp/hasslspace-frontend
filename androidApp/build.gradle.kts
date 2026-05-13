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

val serverLiveKitUrl: String by extra {
    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { properties.load(it) }
    }
    val value = properties.getProperty("LIVEKIT_URL", "")
    if (value.isEmpty()) {
        throw InvalidUserDataException("LiveKit URL is not provided")
    }
    value
}

val centrifugoUrl: String by extra {
    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { properties.load(it) }
    }
    val value = properties.getProperty("CENTRIFUGO_URL", "")
    if (value.isEmpty()) {
        throw InvalidUserDataException("Centrifugo URL is not provided")
    }
    value
}

val appMetricaApiKey: String by extra {
    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { properties.load(it) }
    }
    val value = properties.getProperty("APPMETRICA_API_KEY", "")
    if (value.isEmpty()) {
        throw InvalidUserDataException("AppMetrica API key is not provided. Set your API key in the project's local.properties file: APPMETRICA_API_KEY=<your-api-key-value>.")
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
    namespace = "ru.hse.app.hasslspace"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        targetSdk = 36

        applicationId = "ru.hse.app.hasslspace"
        versionCode = 1
        versionName = "1.0.0"

        buildConfigField("String", "SERVER_URL", "\"$serverUrl\"")
        buildConfigField("String", "LIVEKIT_URL", "\"$serverLiveKitUrl\"")
        buildConfigField("String", "CENTRIFUGO_URL", "\"$centrifugoUrl\"")
        buildConfigField("String", "APPMETRICA_API_KEY", "\"$appMetricaApiKey\"")
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
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.kermit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.compose.nav3)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil)
    implementation(libs.coil.network.ktor)
    implementation(libs.multiplatformSettings)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kstore)
    implementation(libs.materialKolor)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.kstore.file)

    implementation(libs.androidx.activity.compose.v161)
    implementation(libs.androidx.appcompat.v170)
    // Jackson
    implementation(libs.converter.jackson)
    implementation(libs.jackson.module.kotlin.v2183)
    implementation(libs.jackson.datatype.jsr310.v2183)

    //Logging
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json.v163)

    implementation(libs.retrofit)
    implementation(libs.converter.scalars)
    implementation(libs.jetbrains.kotlin.metadata.jvm)

//    implementation(project(":sharedUI"))
    implementation(libs.androidx.activityCompose)

    implementation(libs.androidx.material.icons.extended.android)

    implementation(libs.appcompat)

    // Color Picker
    implementation(libs.colorpicker.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Hilt and kapt
    implementation(libs.hilt.android.v2571)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.jetbrains.kotlin.metadata.jvm)
    ksp(libs.hilt.android.compiler.v2571)


    //Coil
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.palette.ktx)
    implementation(libs.coil.video)

    //Image Cropper
    implementation(libs.ucrop)

    //Easy Permissions
    implementation(libs.easypermissions)


    // emoji
    implementation(libs.androidx.emoji2.emojipicker)
    implementation(libs.androidx.emoji2)
    implementation(libs.androidx.emoji2.views)
    implementation(libs.androidx.emoji2.views.helper)

    // LiveKit
    implementation(libs.livekit.lib)
    implementation(libs.livekit.components)
    // Runtime permissions for Compose
    implementation(libs.accompanist.permissions)

    // ConstraintLayout for call layout
    implementation(libs.constraintlayout.compose)

    // room

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // centrifugo
    implementation(libs.centrifuge.java)

    // App Metrica
    implementation(libs.analytics)
}
