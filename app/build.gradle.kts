plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.oss.licenses)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.angelo.destinystatusapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.angelo.destinystatusapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        create("debugMini") {
            initWith(buildTypes["debug"])
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Browser
    implementation(libs.androidx.browser)

    // DataStore
    implementation(libs.androidx.datastore)

    // Protobuf
    implementation(libs.protobuf.java.lite)
    implementation(libs.protobuf.kotlin.lite)

    // Jetpack Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.navigation.compose)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)

    // OkHttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

    // Moshi
    implementation(libs.moshi)

    // Immutable collections
    implementation(libs.kotlinx.collections)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Lottie
    implementation(libs.lottie.compose)

    // Timber
    implementation(libs.timber)

    // OSS licenses
    implementation(libs.oss.licenses)

    // Slack lint checks
    lintChecks(libs.compose.lint.checks)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    detektPlugins(libs.detekt.formatting)
}

protobuf {
    protoc {
        artifact = "${libs.protobuf.compiler.get()}"
    }
    plugins {
        generateProtoTasks {
            all().forEach {
                it.builtins {
                    create("java") {
                        option("lite")
                    }
                    create("kotlin") {
                        option("lite")
                    }
                }
            }
        }
    }
}
