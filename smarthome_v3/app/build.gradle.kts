plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") version "1.8.22" // Kotlin 플러그인과 버전 명시
}

android {
    namespace = "com.example.smarthome_v3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.smarthome_v3"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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

    // 테스트 옵션 설정 (Kotlin DSL)
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.vectordrawable:vectordrawable:1.1.0")
    implementation ("io.github.ParkSangGwon:tedpermission:2.3.0")
    implementation ("androidx.activity:activity-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.core:core:1.13.0")
    implementation ("com.google.android.material:material:1.8.0")

    // 추가된 테스트 의존성 (Kotlin DSL)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}