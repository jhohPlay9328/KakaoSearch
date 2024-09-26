// Top-level build file where you can add configuration options common to all sub-projects/modules.
extra.apply {
    set("compileSdk_version", 34)
    set("minSdk_version", 24)
    set("targetSdk_version", 34)
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kapt) apply false
}

tasks.register("clean") {
    delete(rootProject.layout.buildDirectory)
}
