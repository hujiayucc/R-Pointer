import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.hujiayucc.rpointer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hujiayucc.rpointer"
        minSdk = 24
        //noinspection EditedTargetSdkVersion
        targetSdk = 34
        versionCode = 100
        versionName = "1.0.0"
        project.archivesName = "${rootProject.name}-${versionName}(${versionCode})"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        debug {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    //noinspection GradleDependency
    implementation("com.google.android.material:material:1.5.0")

    val yukiVersion = "1.2.0"
    implementation("com.highcapable.yukihookapi:api:$yukiVersion")
    ksp("com.highcapable.yukihookapi:ksp-xposed:$yukiVersion")
    compileOnly("de.robv.android.xposed:api:82")
}