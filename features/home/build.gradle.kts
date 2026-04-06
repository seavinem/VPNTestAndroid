plugins {
    alias(libs.plugins.vpn.android.library)
    alias(libs.plugins.vpn.android.library.compose)
    alias(libs.plugins.vpn.hilt)
}

android {
    namespace = "com.org.features.home"
}

dependencies {
    implementation(projects.core.designSystem)
    implementation(projects.core.vpn)

    implementation(projects.domain.home)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.coil.compose)
}
