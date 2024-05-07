plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.serialization)
    
    // Navigation Safe Args
    alias(libs.plugins.androidx.navigation.safeArgs)

    // Hilt
    alias(libs.plugins.hilt.android)

    // Kapt
    alias(libs.plugins.kapt)

    // KSP
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.hawk0f.itutor.features.register"
    compileSdk = 34

    defaultConfig {
        minSdk = 31

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))

    implementation(project(":navigation"))

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}