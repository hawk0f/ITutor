plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("org.jetbrains.kotlin.plugin.serialization") version libs.versions.kotlin apply true
    
    // Navigation Safe Args
    alias(libs.plugins.androidx.navigation.safeArgs)

    // KSP
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.hawk0f.features.register"
    compileSdk = 34

    defaultConfig {
        minSdk = 31
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))
}