plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.nikit.exchangerates"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nikit.exchange"
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf("-Xcontext-receivers")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":rest"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.activity)
    implementation(libs.compose.material)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.uiTooling)
    implementation(libs.compose.uiToolingPreview)
    implementation(libs.compose.viewmodel)

    implementation(libs.retrofit.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.logging)
    implementation(libs.okhttp.okhttp)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.persistance.runtime)
    annotationProcessor(libs.room.persistance)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.hilt.android)
    implementation(libs.dagger.navigation)
    annotationProcessor(libs.dagger.compiler)

    implementation(libs.utils.timber)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}