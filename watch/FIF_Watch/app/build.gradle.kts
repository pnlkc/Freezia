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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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
    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.tiles)
    implementation(libs.tiles.material)
    implementation(libs.horologist.compose.tools)
    implementation(libs.horologist.tiles)
    implementation(libs.watchface.complications.data.source.ktx)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // 화면 이동 라이브러리
    implementation(libs.compose.navigation)

    // 화면 미리보기 라이브러리
    implementation(libs.wear.tooling.preview)

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
}

kapt {
    correctErrorTypes = true
}