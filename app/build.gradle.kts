plugins {
    alias(libs.plugins.androidApplication)
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
    namespace = "dev.hawk0f.itutor"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.hawk0f.itutor"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    //Core
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))
    implementation(project(":core:data"))

    //Features
    implementation(project(":features:students"))
    implementation(project(":features:auth"))
    implementation(project(":features:register"))
    implementation(project(":features:mainContent"))
    implementation(project(":features:lessons"))

    implementation(project(":navigation"))

    //Activity
    implementation(libs.androidx.activity)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}

tasks.whenTaskAdded {
    val kotlinPath = "/src/main/kotlin/dev/hawk0f/itutor/navigation/"
    val navigationArgsPath = "/build/generated/source/navigation-args"
    val appNavigation = "${project(":app").projectDir.path}$navigationArgsPath"
    val navigationPath = "${project(":navigation").projectDir.path}$kotlinPath"

    if (this.name.contains("generateSafeArgs"))
    {
        println("Added dependency to task " + this.name)
        this.doLast {
            fileTree(appNavigation).filter { it.isFile && it.name.contains("Directions") || it.name.contains("Args") }.forEach { file ->
                    println("Changing ${file.name} navigation file")
                    if (file.exists())
                    {
                        val lines = file.readLines().toMutableList()
                        lines.removeAt(0)
                        lines.add(0, "package dev.hawk0f.itutor.navigation")
                        lines.remove("import dev.hawk0f.itutor.R")
                        lines.add(2, "import dev.hawk0f.itutor.navigation.R")
                        file.writeText(lines.joinToString("\n"))
                    }
                }
            move(file(appNavigation), file(navigationPath))
        }
    }
}

private fun move(sourceFile: File, destFile: File)
{
    if (sourceFile.isDirectory)
    {
        val files = sourceFile.listFiles()!!
        for (file in files) move(file, File(destFile, file.name))
        if (!sourceFile.delete()) throw RuntimeException()
    }
    else
    {
        if (!destFile.parentFile.exists()) if (!destFile.parentFile.mkdirs()) destFile.parentFile.delete()
        if (!sourceFile.renameTo(destFile))
        {
            destFile.delete()
            if (!sourceFile.renameTo(destFile)) throw RuntimeException()
        }
    }
}