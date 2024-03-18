plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.googleDaggerHiltAndroid)
    alias(libs.plugins.kotlinPluginSerialization)
}

android {
    namespace = "com.s005.fif"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.s005.fif"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt - DI 라이브러리
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    // okhttp - 네트워크 통신 (기본 네트워크 통신, 헤더 자동 설정) 라이브러리
    implementation(libs.okhttp)

    // Retrofit2 - 네트워크 통신 (네트워크 통신 간편화) 라이브러리
    implementation(libs.retrofit)

    // kotlinx-serialization - JSON 직렬화, 역직렬화 라이브러리
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.converter.scalars)

    // Preferences Datastore - 간단 내부 저장소 라이브러리
    implementation(libs.datastore.preferences)

    // Glide-Compose - 이미지 처리 라이브러리
    implementation(libs.glide.compose)

    // drawablepainter - 이미지 변환 라이브러리
    implementation(libs.drawable.painter)

    // FCM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging.ktx)
}

kapt {
    correctErrorTypes = true
}