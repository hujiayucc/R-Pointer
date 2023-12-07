pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://api.xposed.info/")
        maven(url = "https://s01.oss.sonatype.org/content/repositories/releases/")
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "R-Pointer"
include(":app")
