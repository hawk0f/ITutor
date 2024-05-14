plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    // Kotlin
    api(libs.kotlinx.coroutines.core)

    // Javax Inject
    api(libs.javax.inject)
    implementation(libs.androidx.annotation.jvm)
}