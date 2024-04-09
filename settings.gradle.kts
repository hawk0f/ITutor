pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "ITutor"
include(":app")
include(":core")
include(":core:data")
include(":core:presentation")
include(":core:domain")
include(":features")
include(":features:students")
include(":features:auth")
include(":features:register")
include(":features:mainContent")
