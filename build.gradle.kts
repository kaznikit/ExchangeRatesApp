
plugins{
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.plugin.android)
        classpath(libs.plugin.kotlin)
        classpath(libs.plugin.hilt)
    }
}