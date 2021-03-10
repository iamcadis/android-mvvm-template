plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    defaultConfig {
        applicationId = "com.apps"
        compileSdk = BuildConfigFields.COMPILE
        minSdk = BuildConfigFields.MINIMAL
        targetSdk = BuildConfigFields.TARGET
        versionCode = BuildConfigFields.VERSION_CODE
        versionName = BuildConfigFields.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    signingConfigs {
        create("release") {
            storeFile = BuildConfigFields.getAndroidKeystoreFile(System.getenv("ANDROID_KEYSTORE"))
            storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD")
            keyAlias = System.getenv("ANDROID_KEY_ALIAS")
            keyPassword = System.getenv("ANDROID_KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_API", BuildConfigFields.URL_DEVELOPMENT)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            buildConfigField("String", "BASE_API", BuildConfigFields.URL_PRODUCTION)
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    flavorDimensions("default")

    productFlavors {
        create("dev") {
            dimension("default")
        }
        create("prod") {
            dimension("default")
        }
    }

    compileOptions {
        this.sourceCompatibility = JavaVersion.VERSION_1_8
        this.targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.Kotlin.coroutinesAndroid)

    // Android
    implementation(Libs.Android.appcompat)
    implementation(Libs.Android.activityKtx)
    implementation(Libs.Android.coreKtx)
    implementation(Libs.Android.fragmentKtx)
    implementation(Libs.Android.lifecycleLiveData)
    implementation(Libs.Android.lifecycleViewModel)
    implementation(Libs.Android.multidex)
    implementation(Libs.Android.navFragment)
    implementation(Libs.Android.navUI)

    // Hilt + Dagger + ViewModel
    implementation(Libs.Hilt.dagger)
    kapt(Libs.Hilt.daggerCompiler)

    // UI
    implementation(Libs.UI.constraintLayout)
    implementation(Libs.UI.materialDesign)
    implementation(Libs.UI.coilImageLoader)

    // Network
    implementation(Libs.Network.okhttp)
    implementation(Libs.Network.loggingInterceptor)
    implementation(Libs.Network.retrofit)
    implementation(Libs.Network.retrofitConverter)
    implementation(Libs.Network.moshi)

    // Testing
    testImplementation(Libs.Testing.jUnit)
    androidTestImplementation(Libs.Testing.extJUnit)
    androidTestImplementation(Libs.Testing.espresso)
}