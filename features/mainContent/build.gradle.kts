plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.serialization)

    // Navigation Safe Args
    alias(libs.plugins.androidx.navigation.safeArgs)

    // Hilt
    alias(libs.plugins.hilt.android)

    // KSP
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.hawk0f.itutor.features.maincontent"
    compileSdk = 34

    defaultConfig {
        minSdk = 31

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
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

    implementation(project(":features:students"))
    implementation(project(":features:lessons"))

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    ksp(libs.hilt.compiler)
}

tasks.whenTaskAdded {
    val kotlinPath = "/src/main/kotlin/dev/hawk0f/itutor/navigation/"
    val navigationArgsPath = "/build/generated/source/navigation-args"
    val mainContentNavigation = "${project(":features:mainContent").projectDir.path}$navigationArgsPath"
    val navigationPath = "${project(":navigation").projectDir.path}$kotlinPath"

    if (this.name.contains("generateSafeArgs"))
    {
        println("Added dependency to task " + this.name)
        this.doLast {
            fileTree(mainContentNavigation).filter { it.isFile && it.name.contains("Directions") || it.name.contains("Args") }.forEach { file ->
                println("Changing ${file.name} navigation file")
                if (file.exists())
                {
                    val lines = file.readLines().toMutableList()
                    lines.removeAt(0)
                    lines.add(0, "package dev.hawk0f.itutor.navigation")
                    lines.remove("import dev.hawk0f.itutor.features.maincontent.R")
                    lines.add(2, "import dev.hawk0f.itutor.navigation.R")
                    file.writeText(lines.joinToString("\n"))
                }
            }
            move(file(mainContentNavigation), file(navigationPath))
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