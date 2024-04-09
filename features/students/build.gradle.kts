plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // Navigation Safe Args
    alias(libs.plugins.androidx.navigation.safeArgs)

    // KSP
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.hawk0f.features.students"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))
}