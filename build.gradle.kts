// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.0.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger_hilt_android}")
        classpath ("android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

plugins {
    id("de.fayard.buildSrcVersions") version "0.7.0"
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete (rootProject.buildDir)
}