plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.shopmagazine"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.shopmagazine"
        minSdk = 24
        targetSdk = 36
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
        compose = true
    }
}

dependencies {
    // MOCKK (Для создания поддельных объектов)
    testImplementation("io.mockk:mockk:1.13.8")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    // --- 2. ROOM TESTING (Для базы) ---
    androidTestImplementation("androidx.room:room-testing:2.6.1")

    // --- 3. COROUTINES TEST (Для suspend функций) ---
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // --- 4. ASSERTIONS (Удобные проверки) ---
    androidTestImplementation("com.google.truth:truth:1.1.5")
    testImplementation("com.google.truth:truth:1.1.5")


    //hilt
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
    //room
    implementation("androidx.compose.material:material-icons-extended:1.6.3")

    implementation("androidx.room:room-ktx:2.8.4")
    implementation("androidx.room:room-runtime:2.8.4")
    kapt("androidx.room:room-compiler:2.8.4")
    implementation("androidx.datastore:datastore-preferences:1.2.0")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    //token
    implementation("androidx.work:work-runtime-ktx:2.11.0")
    //ui
    implementation("androidx.core:core-splashscreen:1.0.1")
    //navigation
    implementation("com.valentinilk.shimmer:compose-shimmer:1.3.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}