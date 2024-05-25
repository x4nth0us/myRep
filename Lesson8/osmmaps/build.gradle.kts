plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "ru.mirea.makarovra.osmmaps"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.mirea.makarovra.osmmaps"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    buildFeatures {
        viewBinding= true
    }
}

dependencies {
    implementation("org.osmdroid:osmdroid-android:6.1.16")
// библиотека для хранения данных SharedPreferences
    implementation("androidx.preference:preference:1.2.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}