plugins {
    alias(libs.plugins.vpn.android.library)
    alias(libs.plugins.vpn.hilt)
}

android {
    namespace = "com.org.core.connectivity"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
