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
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    implementation("com.google.android.material:material:1.5.0")

    val yukiVersion = "1.2.0"
    implementation("com.highcapable.yukihookapi:api:$yukiVersion")
    ksp("com.highcapable.yukihookapi:ksp-xposed:$yukiVersion")
    compileOnly("de.robv.android.xposed:api:82")
}