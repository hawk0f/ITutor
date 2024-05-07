// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false

    // Navigation Safe Args
    alias(libs.plugins.androidx.navigation.safeArgs) apply false

    // Hilt
    alias(libs.plugins.hilt.android) apply false

    // Kapt
    alias(libs.plugins.kapt) apply false

    // KSP
    alias(libs.plugins.ksp) apply false

    alias(libs.plugins.androidLibrary) apply false

    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    alias(libs.plugins.serialization) apply false
}