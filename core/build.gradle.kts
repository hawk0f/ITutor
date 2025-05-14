plugins {
    alias(libs.plugins.androidLibrary)
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
    namespace = "dev.hawk0f.itutor.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 29
    }

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL","\"${project.properties["ITutorApiBaseUrl"]}\"")
        }

        debug {
            buildConfigField("String", "BASE_URL", "\"${project.properties["ITutorApiBaseUrl"]}\"")
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
        buildConfig = true
    }
}

dependencies {
    api(platform(libs.androidx.compose.bom))
    androidTestApi(platform(libs.androidx.compose.bom))

    // Compose
    api(libs.androidx.runtime)
    api(libs.androidx.ui)
    api(libs.androidx.foundation)
    api(libs.androidx.foundation.layout)
    api(libs.androidx.material3)
    api(libs.androidx.material.icons.extended)
    api(libs.androidx.runtime.livedata)
    api(libs.androidx.ui.tooling)
    api(libs.androidx.lifecycle.viewmodel.compose)

    // Kotlin
    api(libs.kotlinx.coroutines.android)
    api(libs.kotlinx.coroutines.core)

    // UI Components
    api(libs.android.material)
    api(libs.androidx.constraintLayout)

    // Core
    api(libs.androidx.core)
    api(libs.androidx.core.splashscreen)

    // Activity
    api(libs.androidx.activity)
    api(libs.androidx.activity.compose)

    // Fragment
    api(libs.androidx.fragment)

    // Lifecycle
    api(libs.androidx.lifecycle.viewModel)
    api(libs.androidx.lifecycle.runtime)

    // Navigation
    api(libs.androidx.navigation.fragment)
    api(libs.androidx.navigation.ui)

    // Javax Inject
    api(libs.javax.inject)
    api(libs.androidx.annotation.jvm)

    // Kotlin
    api(libs.kotlinx.serialization)

    // Crypto Preferences
    implementation(libs.androidx.security.crypto)

    // Retrofit
    api(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)

    // OkHttp
    implementation(platform(libs.okHttp.bom))
    implementation(libs.okHttp)
    implementation(libs.okHttp.loggingInterceptor)

    //work
    api(libs.androidx.work.runtime)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)
}