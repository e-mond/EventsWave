// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.11.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}