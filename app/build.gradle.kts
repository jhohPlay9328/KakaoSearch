plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
}
android {
    namespace = "com.jhoh.play.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jhoh.play.search"
        minSdk = rootProject.extra["minSdk_version"] as Int
        targetSdk = rootProject.extra["targetSdk_version"] as Int
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-kakao-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.androidx.swiperefreshlayout)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)

    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.ui)
    ksp(libs.androidx.databinding.compiler)

    implementation(libs.androidx.ktx)
    implementation(libs.material)
    implementation(libs.hilt.android)
    implementation(libs.glide)
    ksp(libs.hilt.android.compiler)

    implementation(libs.timber)
}