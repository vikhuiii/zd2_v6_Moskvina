plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // 1. Добавляем плагин компилятора Compose (версия должна совпадать с версией Kotlin, здесь 2.0.21)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    // 2. Обновляем версию KSP под Kotlin 2.0+
    id("com.google.devtools.ksp") version "2.0.21-1.0.28"
}

android {
    namespace = "com.example.zd2_v6_moskvina.presentation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zd2_v6_moskvina"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    // ВАЖНО: Блок composeOptions { kotlinCompilerExtensionVersion = ... } УДАЛЕН,
    // так как теперь за это отвечает плагин выше.

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.wear.compose:compose-material:1.4.0")
    implementation("androidx.wear.compose:compose-foundation:1.4.0")
    implementation("androidx.wear.compose:compose-ui-tooling:1.4.0")
    implementation("androidx.compose.material:material:1.6.0")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}