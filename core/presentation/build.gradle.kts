plugins {
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.hawk0f.core.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 31
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
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