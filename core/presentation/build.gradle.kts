plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // Hilt
    alias(libs.plugins.hilt.android)

    // KSP
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.hawk0f.itutor.core.presentation"

    compileSdk = 34

    defaultConfig {
        minSdk = 30
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
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

    // Animators
    api(libs.recycler.view.animators)

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

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}