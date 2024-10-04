plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Kotlin
    api(libs.kotlinx.coroutines.core)

    // Javax Inject
    api(libs.javax.inject)
    implementation(libs.androidx.annotation.jvm)
}