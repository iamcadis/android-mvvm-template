private object Versions {

    // Gradle
    const val gradle = "4.1.2"
    const val kotlin = "1.4.31"
    const val safeArgs = "2.3.3"
    const val googleServices = "4.3.5"
    const val daggerHilt = "2.33-beta"
    const val crashlytics = "2.5.1"

    // Kotlin Coroutines
    const val coroutinesAndroid = "1.3.9"

    // Android
    const val appcompat = "1.2.0"
    const val coreKtx = "1.3.2"
    const val multidex = "2.0.1"

    // Hilt
    const val hiltViewModel = "1.0.0"

    // UI
    const val constraintLayout = "2.0.4"
    const val materialDesign = "1.3.0"

    // Network
    const val okhttp3 = "4.9.0"
    const val retrofit2 = "2.9.0"
    const val moshi = "1.11.0"
}

object Libs {

    object Gradle {
        const val android = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.safeArgs}"
        const val googleService = "com.google.gms:google-services:${Versions.googleServices}"
        const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics}"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    }

    object Android {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    }

    object Hilt {
        const val dagger = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
        const val daggerCompiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
    }

    object UI {
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    }

    object Network {
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
        const val loggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
        const val retrofitConverter =
            "com.squareup.retrofit2:converter-scalars:${Versions.retrofit2}"
        const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    }

    object Testing {
        const val jUnit = "junit:junit:4.13.1"
        const val extJUnit = "androidx.test.ext:junit:1.1.2"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    }
}