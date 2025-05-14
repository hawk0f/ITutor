plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.serialization)

    // Hilt
    alias(libs.plugins.hilt.android)

    // KSP
    alias(libs.plugins.ksp)

    //Compose
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.hawk0f.itutor"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.hawk0f.itutor"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
        applicationIdSuffix = ".debug"
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        debug {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    //Core
    implementation(project(":core"))

    //Charts
    implementation(libs.mp.android.chart)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)
}