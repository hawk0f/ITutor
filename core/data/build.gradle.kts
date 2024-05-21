plugins {
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.serialization)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // KSP
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.hawk0f.itutor.core.data"

    compileSdk = 34

    defaultConfig {
        minSdk = 31
    }

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"http://212.113.120.163:5110/api/\"")
        }

        debug {
            buildConfigField("String", "BASE_URL", "\"http://212.113.120.163:5110/api/\"")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":core:domain"))

    // Kotlin
    api(libs.kotlinx.serialization)

    implementation(libs.androidx.security.crypto)

    // Retrofit
    api(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)

    // OkHttp
    implementation(platform(libs.okHttp.bom))
    implementation(libs.okHttp)
    implementation(libs.okHttp.loggingInterceptor)
}