// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.0.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger_hilt_android}")
        classpath ("org.jetbrains.kotlin:kotlin-allopen:${Versions.kotlin}")
        classpath ("android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.4.1")
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

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent

    // Optionally configure plugin
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}

tasks.register("clean", Delete::class) {
    delete (rootProject.buildDir)
}