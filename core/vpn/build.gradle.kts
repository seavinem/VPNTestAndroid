plugins {
    alias(libs.plugins.vpn.android.library)
    alias(libs.plugins.vpn.hilt)
}

android {
    namespace = "com.org.core.vpn"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}

