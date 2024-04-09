plugins {
    alias(libs.plugins.androidApplication)

    // KSP
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.hawk0f.itutor"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.hawk0f.itutor"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    //Core
    implementation(project(":core:presentation"))
    implementation(project(":core:data"))

    //Features
    implementation(project(":features:students"))
    implementation(project(":features:auth"))

    //Activity
    implementation(libs.androidx.activity)

    //Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}