plugins {
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.kotlin.plugin.serialization") version libs.versions.kotlin apply true

    // KSP
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.hawk0f.core.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 31
    }

    buildTypes {
        getByName("release") {
            buildConfigField("String", "BASE_URL", "\"http://192.168.0.185:5110/api/\"")
        }

        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"http://192.168.0.185:5110/api/\"")
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "18"
    }
}

dependencies {
    implementation(project(":core:domain"))

    // Kotlin
    api(libs.kotlinx.serialization)

    // Retrofit
    api(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization.converter)

    // OkHttp
    implementation(platform(libs.okHttp.bom))
    implementation(libs.okHttp)
    implementation(libs.okHttp.loggingInterceptor)
}