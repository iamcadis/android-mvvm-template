buildscript {

    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }

    dependencies {
        classpath(Libs.Gradle.android)
        classpath(Libs.Gradle.kotlin)
        classpath(Libs.Gradle.safeArgs)
        classpath(Libs.Gradle.googleService)
        classpath(Libs.Gradle.daggerHilt)
        classpath(Libs.Gradle.crashlytics)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}