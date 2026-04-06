plugins {
    alias(libs.plugins.vpn.android.library)
    alias(libs.plugins.vpn.android.library.compose)
    alias(libs.plugins.vpn.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.org.core.network"
}

dependencies {
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.kotlin.serialization.json)
}
