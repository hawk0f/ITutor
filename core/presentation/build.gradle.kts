plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // Kapt
    alias(libs.plugins.kapt)
}

android {
    namespace = "dev.hawk0f.itutor.core.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 31
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":core:domain"))

    // Kotlin
    api(libs.kotlinx.coroutines.android)

    // UI Components
    api(libs.android.material)
    api(libs.androidx.constraintLayout)
    api(libs.android.vbpd)

    // Core
    api(libs.androidx.core)
    api(libs.androidx.core.splashscreen)

    // Activity
    api(libs.androidx.activity)

    // Fragment
    api(libs.androidx.fragment)

    // Lifecycle
    api(libs.androidx.lifecycle.viewModel)
    api(libs.androidx.lifecycle.runtime)

    // Navigation
    api(libs.androidx.navigation.fragment)
    api(libs.androidx.navigation.ui)
}