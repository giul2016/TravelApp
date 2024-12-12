plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
   // alias(libs.plugins.googleServices)
    alias(libs.plugins.daggerHilt)
    kotlin("kapt")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.devgiul.mychofer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.devgiul.mychofer"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)



    // Retrofit para chamadas de API
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Jetpack Navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    // ViewModel e LiveData
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    // Material Design
    implementation (libs.material.v190)

    // Glide para carregamento de imagens (mapa estático, por exemplo)
    implementation (libs.glide)
    kapt ("com.github.bumptech.glide:compiler:4.15.1")

    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.libraries.places:places:2.6.0")
    implementation ("com.google.android.gms:play-services-location:18.0.0")

}