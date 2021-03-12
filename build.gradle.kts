buildscript {

    val kotlin_version by extra("1.4.31")
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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
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