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
        minSdk = 30
    }

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL","\"${project.properties["ITutorApiBaseUrl"]}\"")
        }

        debug {
            buildConfigField("String", "BASE_URL", "\"${project.properties["ITutorApiBaseUrl"]}\"")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
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